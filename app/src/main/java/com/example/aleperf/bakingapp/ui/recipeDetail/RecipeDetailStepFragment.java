package com.example.aleperf.bakingapp.ui.recipeDetail;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aleperf.bakingapp.BakingApplication;
import com.example.aleperf.bakingapp.model.Recipe.Step;
import com.example.aleperf.bakingapp.utils.StepFieldsValidator;
import com.example.aleperf.bakingapp.R;
import com.example.aleperf.bakingapp.model.Recipe;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;


import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Shows details of a single step of a recipe: long description and video, if present.
 */

public class RecipeDetailStepFragment extends Fragment implements Player.EventListener {

    private static final String TAG = RecipeDetailStepFragment.class.getSimpleName();


    private static final String STEP_EXTRA_POSITION = "step extra position";
    private static final String RECIPE_EXTRA_ID = "recipe extra id";
    private static final String APPLICATION_NAME = "Bei-Bake!";
    private static final String PLAYBACK_POSITION = "playback position";
    private static final String CURRENT_WINDOW = "current window";
    private static final String PLAY_WHEN_READY = "play when ready";

    private int recipeId;
    private int stepPosition;
    private RecipeDetailViewModel viewModel;
    private List<Step> steps;
    private LiveData<Recipe> recipe;
    private Unbinder unbinder;
    @Inject
    ViewModelProvider.Factory viewModelProviderFactory;

    private SimpleExoPlayer exoPlayer;
    private long playbackPosition;
    private int currentWindow;
    private boolean playWhenReady = true;

    private Uri videoUri;

    private MediaSessionCompat mediaSession;
    private PlaybackStateCompat.Builder stateBuilder;

    @BindView(R.id.step_video)
    PlayerView playerView;
    @BindView(R.id.step_title)
    TextView stepTitle;
    @BindView(R.id.step_number)
    TextView stepNumber;
    @BindView(R.id.step_long_description)
    TextView stepDescription;
    @BindView(R.id.arrowLeft)
    ImageButton arrowLeft;
    @BindView(R.id.arrowRight)
    ImageButton arrowRight;
    @BindView(R.id.recipeThumbnail)
    ImageView thumbnail;


    public static RecipeDetailStepFragment getInstance(int recipeId, int stepPosition) {
        Bundle bundle = new Bundle();
        bundle.putInt(STEP_EXTRA_POSITION, stepPosition);
        bundle.putInt(RECIPE_EXTRA_ID, recipeId);
        RecipeDetailStepFragment fragment = new RecipeDetailStepFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((BakingApplication) getActivity().getApplication()).getBakingApplicationComponent().inject(this);
        setRetainInstance(true);
        if (savedInstanceState == null) {
            Bundle bundle = this.getArguments();
            recipeId = bundle.getInt(RECIPE_EXTRA_ID);
            stepPosition = bundle.getInt(STEP_EXTRA_POSITION);

        } else {
            recipeId = savedInstanceState.getInt(RECIPE_EXTRA_ID);
            stepPosition = savedInstanceState.getInt(STEP_EXTRA_POSITION);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_step_detail, container, false);
        unbinder = ButterKnife.bind(this, root);

        if (savedInstanceState != null) {
            recipeId = savedInstanceState.getInt(RECIPE_EXTRA_ID);
            stepPosition = savedInstanceState.getInt(STEP_EXTRA_POSITION);
            playbackPosition = savedInstanceState.getLong(PLAYBACK_POSITION, C.TIME_UNSET);
            currentWindow = savedInstanceState.getInt(CURRENT_WINDOW, 0);
            playWhenReady = savedInstanceState.getBoolean(PLAY_WHEN_READY, false);

        }
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this, viewModelProviderFactory).get(RecipeDetailViewModel.class);
        recipe = viewModel.getRecipe(recipeId);
        subscribe();


    }

    private void subscribe() {
        Observer<Recipe> observer = recipe -> {
            if (recipe != null) {
                steps = recipe.getSteps();
                updateUI(stepPosition);
            }
        };
        recipe.observe(this, observer);
    }

    private void updateUI(int stepPosition) {
        Step step = steps.get(stepPosition);
        String shortDescription = step.getShortDescription();
        String longDescription = step.getDescription();
        stepTitle.setText(shortDescription);
        stepDescription.setText(longDescription);
        videoUri = StepFieldsValidator.getVideoUri(step);
        if (videoUri != null) {
            playerView.setVisibility(View.VISIBLE);
            initializeMediaSession();
            initializePlayer();
        } else {
            playerView.setVisibility(View.GONE);

        }
        //TODO Load image with Picasso
    }

    private void initializePlayer() {
        if (exoPlayer == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            exoPlayer = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(getActivity()), trackSelector, loadControl);
            playerView.setPlayer(exoPlayer);
            exoPlayer.addListener(this);
            exoPlayer.setPlayWhenReady(playWhenReady);
            exoPlayer.seekTo(currentWindow, playbackPosition);
            MediaSource mediaSource = buildExoPlayerMediaSource(videoUri);
            exoPlayer.prepare(mediaSource);
            exoPlayer.setPlayWhenReady(playWhenReady);
        } else {
            exoPlayer.setPlayWhenReady(playWhenReady);
            exoPlayer.seekTo(currentWindow, playbackPosition);

        }

    }

    private void releasePlayer() {
        if (exoPlayer != null) {
            playbackPosition = exoPlayer.getCurrentPosition();
            currentWindow = exoPlayer.getCurrentWindowIndex();
            playWhenReady = exoPlayer.getPlayWhenReady();
            exoPlayer.release();
            exoPlayer = null;
        }
    }

    private MediaSource buildExoPlayerMediaSource(Uri videoUri) {
        String userAgent = Util.getUserAgent(getActivity(), APPLICATION_NAME);
        DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getActivity(), userAgent, bandwidthMeter);
        return new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(videoUri);

    }

    private void initializeMediaSession() {
        mediaSession = new MediaSessionCompat(getActivity(), TAG);
        mediaSession.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
        mediaSession.setMediaButtonReceiver(null);
        stateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE);

        mediaSession.setPlaybackState(stateBuilder.build());


        // MySessionCallback has methods that handle callbacks from a media controller.
        mediaSession.setCallback(new MySessionCallback());

        // Start the Media Session since the activity is active.
        mediaSession.setActive(true);

    }


    @Override
    public void onResume() {
        super.onResume();
        if (videoUri != null && Util.SDK_INT <= 23 && exoPlayer == null) {
            initializePlayer();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (videoUri != null && Util.SDK_INT > 23 && exoPlayer == null) {
            initializePlayer();
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        if (exoPlayer != null && Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        if (exoPlayer != null && Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(RECIPE_EXTRA_ID, recipeId);
        outState.putInt(STEP_EXTRA_POSITION, stepPosition);
        if (exoPlayer == null) {
            outState.putLong(PLAYBACK_POSITION, playbackPosition);
            outState.putInt(CURRENT_WINDOW, currentWindow);
            outState.putBoolean(PLAY_WHEN_READY, playWhenReady);
        } else {
            outState.putLong(PLAYBACK_POSITION, exoPlayer.getCurrentPosition());
            outState.putInt(CURRENT_WINDOW, exoPlayer.getCurrentWindowIndex());
            outState.putBoolean(PLAY_WHEN_READY, exoPlayer.getPlayWhenReady());
        }
    }

    //the following methods are the implementation of the Player.EventListener interface
    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if ((playbackState == Player.STATE_READY) && playWhenReady) {
            stateBuilder.setState(PlaybackStateCompat.STATE_PLAYING,
                    exoPlayer.getCurrentPosition(), 1f);
        } else if ((playbackState == Player.STATE_READY)) {
            stateBuilder.setState(PlaybackStateCompat.STATE_PAUSED,
                    exoPlayer.getCurrentPosition(), 1f);
        }
        mediaSession.setPlaybackState(stateBuilder.build());

    }

    @Override
    public void onRepeatModeChanged(int repeatMode) {

    }

    @Override
    public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity(int reason) {

    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }

    @Override
    public void onSeekProcessed() {

    }

    private class MySessionCallback extends MediaSessionCompat.Callback {
        @Override
        public void onPlay() {
            exoPlayer.setPlayWhenReady(true);
        }

        @Override
        public void onPause() {
            exoPlayer.setPlayWhenReady(false);
            playbackPosition = exoPlayer.getCurrentPosition();
        }

        @Override
        public void onSkipToPrevious() {
            exoPlayer.seekTo(0);
        }
    }

}

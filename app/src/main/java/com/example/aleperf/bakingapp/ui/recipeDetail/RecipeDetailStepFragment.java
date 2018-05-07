package com.example.aleperf.bakingapp.ui.recipeDetail;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aleperf.bakingapp.BakingApplication;
import com.example.aleperf.bakingapp.model.Recipe.Step;
import com.example.aleperf.bakingapp.utils.RecipeUtilities;
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
import com.squareup.picasso.Picasso;


import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Shows details of a single step of a recipe: long description and video, if present.
 */

public class RecipeDetailStepFragment extends Fragment implements Player.EventListener {

    public static final String TAG = RecipeDetailStepFragment.class.getSimpleName();


    private static final String STEP_EXTRA_POSITION = "step extra position";
    private static final String RECIPE_EXTRA_ID = "recipe extra id";
    private static final String APPLICATION_NAME = "Bei-Bake!";
    private static final String PLAYBACK_POSITION = "playback position";
    private static final String CURRENT_WINDOW = "current window";
    private static final String PLAY_WHEN_READY = "play when ready";
    private static final String VIDEO_DURATION = "exoplayer video duration";
    private static final int PHONE_PORTRAIT = 1;
    private static final int PHONE_LANDSCAPE = 2;
    private static final int TABLET = 3;
    private static final int INTRO_STEP = 0;

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
    private boolean playWhenReady = false;

    private Uri videoUri;

    private MediaSessionCompat mediaSession;
    private PlaybackStateCompat.Builder stateBuilder;
    private long duration;

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
    @BindView(R.id.next_arrow_text_view)
    TextView nextTextView;
    @BindView(R.id.previous_arrow_text_view)
    TextView previousTextView;
    @BindView(R.id.step_video_place_holder)
    ImageView videoPlaceholderImageView;
    @BindView(R.id.no_video_message)
    TextView noVideoMessageTextView;


    public static RecipeDetailStepFragment newInstance(int recipeId, int stepPosition) {
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
            duration = savedInstanceState.getLong(VIDEO_DURATION);

        }

        StepNavigationListener navigationListener = new StepNavigationListener();
        arrowLeft.setOnClickListener(navigationListener);
        arrowRight.setOnClickListener(navigationListener);
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
        int screen_orientation = getResources().getInteger(R.integer.max_screen_switch);
        String shortDescription = step.getShortDescription();
        String longDescription = step.getDescription();
        String thumbnailUrl = step.getThumbnailURL();
        int defaultDrawableId = RecipeUtilities.getImageDefaultId(recipeId - 1);
        stepTitle.setText(shortDescription);
        stepDescription.setText(longDescription);
        if (stepPosition == INTRO_STEP) {
            stepNumber.setText(getString(R.string.intro_step));
        } else {
            stepNumber.setText(String.format(getString(R.string.step_count), stepPosition, steps.size() - 1));
        }
        videoUri = StepFieldsValidator.getVideoUri(step);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (videoUri != null) {
            if (isNetworkAvailable()) {
                playerView.setVisibility(View.VISIBLE);
                videoPlaceholderImageView.setVisibility(View.GONE);
                noVideoMessageTextView.setVisibility(View.GONE);
                initializeMediaSession();
                initializePlayer();
                if (screen_orientation == PHONE_LANDSCAPE) {
                    hideStepInfo();
                    hideSystemUi();
                    actionBar.hide();

                } else {
                    showStepInfo();
                    actionBar.show();
                }
            } else {
                playerView.setVisibility(View.GONE);
                videoPlaceholderImageView.setVisibility(View.VISIBLE);
                noVideoMessageTextView.setVisibility(View.VISIBLE);
                noVideoMessageTextView.setText(getString(R.string.video_placeholder_no_connection));

            }
        } else {
            playerView.setVisibility(View.GONE);
            videoPlaceholderImageView.setVisibility(View.VISIBLE);
            noVideoMessageTextView.setVisibility(View.VISIBLE);
            noVideoMessageTextView.setText(getString(R.string.video_placeholder_no_video));
            showStepInfo();
            actionBar.show();

        }
        if (thumbnailUrl != null && thumbnailUrl.length() > 0) {
            Picasso.get().load(thumbnailUrl).placeholder(defaultDrawableId).error(defaultDrawableId).into(thumbnail);
        } else {
            thumbnail.setImageResource(defaultDrawableId);
        }
    }

    private void showStepInfo() {

        stepTitle.setVisibility(View.VISIBLE);
        stepNumber.setVisibility(View.VISIBLE);
        stepDescription.setVisibility(View.VISIBLE);
        arrowLeft.setVisibility(View.VISIBLE);
        arrowRight.setVisibility(View.VISIBLE);
        thumbnail.setVisibility(View.VISIBLE);
        nextTextView.setVisibility(View.VISIBLE);
        previousTextView.setVisibility(View.VISIBLE);
    }

    private void hideStepInfo() {

        stepTitle.setVisibility(View.GONE);
        stepNumber.setVisibility(View.GONE);
        stepDescription.setVisibility(View.GONE);
        arrowLeft.setVisibility(View.GONE);
        arrowRight.setVisibility(View.GONE);
        thumbnail.setVisibility(View.GONE);
        nextTextView.setVisibility(View.GONE);
        previousTextView.setVisibility(View.GONE);
        }

    @SuppressLint("InlinedApi")
    private void hideSystemUi() {
        playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    private void initializePlayer() {
        if (exoPlayer == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            exoPlayer = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(getActivity()), trackSelector, loadControl);
            MediaSource mediaSource = buildExoPlayerMediaSource(videoUri);
            exoPlayer.prepare(mediaSource);
            exoPlayer.setPlayWhenReady(playWhenReady);
        }
        playerView.setPlayer(exoPlayer);
        exoPlayer.addListener(this);
        exoPlayer.setPlayWhenReady(playWhenReady);
        exoPlayer.seekTo(currentWindow, playbackPosition);

    }

    private void releasePlayer() {
        if (exoPlayer != null) {
            playbackPosition = exoPlayer.getCurrentPosition();
            currentWindow = exoPlayer.getCurrentWindowIndex();
            exoPlayer.setPlayWhenReady(false);
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
        mediaSession.setCallback(new MySessionCallback());
        mediaSession.setActive(true);

    }


    @Override
    public void onResume() {
        super.onResume();
        if (videoUri != null && (Util.SDK_INT <= 23 || exoPlayer == null)) {
            initializePlayer();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (videoUri != null && (Util.SDK_INT > 23 || exoPlayer == null)) {
            initializePlayer();
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        if (exoPlayer != null) {
            playWhenReady = exoPlayer.getPlayWhenReady();
            exoPlayer.setPlayWhenReady(false);
            playbackPosition = exoPlayer.getCurrentPosition();
        }
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(RECIPE_EXTRA_ID, recipeId);
        outState.putInt(STEP_EXTRA_POSITION, stepPosition);
        outState.putLong(VIDEO_DURATION, duration);
        if (exoPlayer == null) {
            outState.putLong(PLAYBACK_POSITION, playbackPosition);
            outState.putInt(CURRENT_WINDOW, currentWindow);
            outState.putBoolean(PLAY_WHEN_READY, playWhenReady);
        } else {
            outState.putLong(PLAYBACK_POSITION, exoPlayer.getCurrentPosition());
            outState.putInt(CURRENT_WINDOW, exoPlayer.getCurrentWindowIndex());
            outState.putBoolean(PLAY_WHEN_READY, playWhenReady);
        }
    }

    //the following methods are the implementation of the Player.EventListener interface
    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray
            trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if ((playbackState == Player.STATE_READY) && playWhenReady) {
            stateBuilder.setState(PlaybackStateCompat.STATE_PLAYING,
                    exoPlayer.getCurrentPosition(), 1f);
            duration = exoPlayer.getDuration();
        } else if ((playbackState == Player.STATE_READY)) {
            stateBuilder.setState(PlaybackStateCompat.STATE_PAUSED,
                    exoPlayer.getCurrentPosition(), 1f);
        } else if(playbackState == Player.STATE_ENDED){
            //if playing has finished, maintain on screen one of the last frames
            // this is necessary to avoid vanishing of the exoPlayer on rotation
            exoPlayer.seekTo(duration - 150);
            exoPlayer.setPlayWhenReady(false);
        }
        mediaSession.setPlaybackState(stateBuilder.build());

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
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

    private class StepNavigationListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            int position = stepPosition;
            int viewId = view.getId();
            boolean isValidPosition = true;
            switch (viewId) {
                case R.id.arrowLeft:
                    --position;
                    if (position < 0) {
                        isValidPosition = false;
                        Toast.makeText(getActivity(), getString(R.string.no_previous_step), Toast.LENGTH_SHORT).show();
                    }
                    break;
                default:
                    ++position;
                    if (position >= steps.size()) {
                        isValidPosition = false;
                        Toast.makeText(getActivity(), getString(R.string.no_next_step), Toast.LENGTH_SHORT).show();
                    }
            }
            if (isValidPosition) {
                if (getActivity() instanceof StepSelector) {
                    ((StepSelector) getActivity()).onStepSelected(position);
                }
            }

        }
    }

}

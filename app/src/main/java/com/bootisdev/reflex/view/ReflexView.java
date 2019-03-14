package com.bootisdev.reflex.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.media.SoundPool;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.logging.Handler;

public class ReflexView extends View {
    // preferences
    private static final String HIGH_SCORE = "HIGH_SCORE";
    private SharedPreferences sharedPreferences;

    // variables for managing the game
    private int spotsTouched;
    private int score;
    private int level;
    private int viewWidth;
    private int viewHeight;
    private long animationTime;
    private boolean gameOver;
    private boolean gamePaused;
    private boolean dialogDisplayed;
    private int highScore;

    // collection types for our spots
    private final Queue<ImageView> spots = new ConcurrentLinkedDeque<>();
    private final Queue<Animation> animators = new ConcurrentLinkedDeque<>();

    private TextView highScoreTextView;
    private TextView currentScoreTextView;
    private TextView levelTextView;
    private LinearLayout lifeLinearLayout;
    private RelativeLayout relativeLayout;
    private Resources resources;
    private LayoutInflater layoutInflater;

    private static final int INITIAL_ANIMATION_DURATION = 6000; // 6 seconds
    private static final Random RANDOM = new Random();
    private static final int SPOT_DIAMETER = 100;
    private static final float SCALE_X = 0.25f;
    private static final float SCALE_Y = 0.25f;
    private static final int INITIAL_SPOTS = 5;
    private static final int SPOT_DELAY = 500;
    private static final int LIVES = 3;
    private static final int MAX_LIVES = 7;
    private static final int NEW_LEVEL = 10;

    private Handler spotHandler;

    private static final int HIT_SOUND_ID = 1;
    private static final int MISS_SOUND_ID = 2;
    private static final int DISAPPEAR_SOUND_ID = 3;
    private static final int SOUND_PRIORITY = 1;
    private static final int SOUND_QUALITY = 100;
    private static final int MAX_STREAMS = 4;

    private SoundPool soundPool;
    private int volume;
    private Map<Integer, Integer> soundMap;

    public ReflexView(Context context, SharedPreferences sharedPreferences, RelativeLayout parentLayout) {
        super(context);
    }
}

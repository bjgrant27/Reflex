package com.bootisdev.reflex.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.media.SoundPool;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bootisdev.reflex.R;

import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedDeque;

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

        this.sharedPreferences = sharedPreferences;
        highScore = sharedPreferences.getInt(HIGH_SCORE, 0);

        // save resources for loading external values
        resources = context.getResources();

        // save layoutInflater
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // setup UI components
        relativeLayout = parentLayout;
        lifeLinearLayout = relativeLayout.findViewById(R.id.lifeLinearLayout);
        highScoreTextView = relativeLayout.findViewById(R.id.highScoreTextView);
        currentScoreTextView = relativeLayout.findViewById(R.id.scoreTextView);
        levelTextView = relativeLayout.findViewById(R.id.levelTextView);

//        spotHandler = new Handler();

        addNewSpot();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        viewWidth = w;
        viewHeight = h;
    }

    public void addNewSpot() {
        // create a new spot
        final ImageView spot = (ImageView) layoutInflater.inflate(R.layout.untouched, null);

        spots.add(spot);

        spot.setLayoutParams(new RelativeLayout.LayoutParams(SPOT_DIAMETER, SPOT_DIAMETER));

        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) spot.getContext().getSystemService(Context.WINDOW_SERVICE);

        windowManager.getDefaultDisplay().getMetrics(metrics);

        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        int x = RANDOM.nextInt(width - SPOT_DIAMETER);
        int y = RANDOM.nextInt(height - SPOT_DIAMETER);
        int x2 = RANDOM.nextInt(width - SPOT_DIAMETER);
        int y2 = RANDOM.nextInt(height - SPOT_DIAMETER);

        spot.setImageResource(RANDOM.nextInt(2) == 0 ? R.drawable.green_spot : R.drawable.red_spot);
        spot.setX(x);
        spot.setY(y);

        spot.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                touchedSpot(spot);
            }
        });

        relativeLayout.addView(spot); // add spot to the screen
    }

    private void touchedSpot(ImageView spot) {
        relativeLayout.removeView(spot);
        spots.remove(spot);

        level = 1;

        ++spotsTouched; // increment number of spots touched
        score += 10 * level;

        currentScoreTextView.setText("Score: " + score);
    }
}

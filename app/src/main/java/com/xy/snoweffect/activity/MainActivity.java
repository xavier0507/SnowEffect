package com.xy.snoweffect.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.xy.snoweffect.R;
import com.xy.snoweffect.view.SnowEffectFrameLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private SnowEffectFrameLayout snowEffectFrameLayout;

    private EditText snowBasicCountEdit;
    private EditText dropAverageDurationEdit;

    private Button cleanParamsButton;
    private Button startSakuraAnimButton;
    private Button startSnowAnimButton;
    private Button startGoldIngotAnimButton;
    private Button startRedEnvelopeAnimButton;
    private Button startCoinAnimButton;
    private Button startMixAnimButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
        this.findUIs();
        this.addEvents();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_clean:
                this.cleanParams();
                break;

            case R.id.button_start_sakura_anim:
                this.snowEffectFrameLayout.clearDrawableList();
                this.snowEffectFrameLayout.addDrawable(R.drawable.sakura);
                this.launchAnim();
                break;

            case R.id.button_start_snow_anim:
                this.snowEffectFrameLayout.clearDrawableList();
                this.snowEffectFrameLayout.addDrawable(R.drawable.snow);
                this.launchAnim();
                break;

            case R.id.button_start_gold_ingot_anim:
                this.snowEffectFrameLayout.clearDrawableList();
                this.snowEffectFrameLayout.addDrawable(R.drawable.gold_ingot);
                this.launchAnim();
                break;

            case R.id.button_start_red_envelope_anim:
                this.snowEffectFrameLayout.clearDrawableList();
                this.snowEffectFrameLayout.addDrawable(R.drawable.red_envelope);
                this.launchAnim();
                break;

            case R.id.button_start_coin_anim:
                this.snowEffectFrameLayout.clearDrawableList();
                this.snowEffectFrameLayout.addDrawable(R.drawable.coin);
                this.launchAnim();
                break;

            case R.id.button_start_mix_anim:
                this.snowEffectFrameLayout.clearDrawableList();
                this.snowEffectFrameLayout.addDrawable(R.drawable.sakura);
                this.snowEffectFrameLayout.addDrawable(R.drawable.snow);
                this.snowEffectFrameLayout.addDrawable(R.drawable.gold_ingot);
                this.snowEffectFrameLayout.addDrawable(R.drawable.red_envelope);
                this.snowEffectFrameLayout.addDrawable(R.drawable.coin);
                this.launchAnim();
                break;
        }
    }

    private void findUIs() {
        this.snowEffectFrameLayout = (SnowEffectFrameLayout) this.findViewById(R.id.activity_main);

        this.snowBasicCountEdit = (EditText) this.findViewById(R.id.edit_snow_basic_count);
        this.dropAverageDurationEdit = (EditText) this.findViewById(R.id.edit_drop_duration);

        this.cleanParamsButton = (Button) this.findViewById(R.id.button_clean);
        this.startSakuraAnimButton = (Button) this.findViewById(R.id.button_start_sakura_anim);
        this.startSnowAnimButton = (Button) this.findViewById(R.id.button_start_snow_anim);
        this.startGoldIngotAnimButton = (Button) this.findViewById(R.id.button_start_gold_ingot_anim);
        this.startRedEnvelopeAnimButton = (Button) this.findViewById(R.id.button_start_red_envelope_anim);
        this.startCoinAnimButton = (Button) this.findViewById(R.id.button_start_coin_anim);
        this.startMixAnimButton = (Button) this.findViewById(R.id.button_start_mix_anim);
    }

    private void addEvents() {
        this.cleanParamsButton.setOnClickListener(this);
        this.startSakuraAnimButton.setOnClickListener(this);
        this.startSnowAnimButton.setOnClickListener(this);
        this.startGoldIngotAnimButton.setOnClickListener(this);
        this.startRedEnvelopeAnimButton.setOnClickListener(this);
        this.startCoinAnimButton.setOnClickListener(this);
        this.startMixAnimButton.setOnClickListener(this);
    }

    private void cleanParams() {
        this.snowBasicCountEdit.setText("");
        this.dropAverageDurationEdit.setText("");
    }

    private void launchAnim() {
        String snowBasicCount = this.snowBasicCountEdit.getText().toString().trim();
        String dropDuration = this.dropAverageDurationEdit.getText().toString().trim();

        if (TextUtils.isEmpty(snowBasicCount) || TextUtils.isEmpty(dropDuration)) {
            this.snowEffectFrameLayout.startEffect();
        } else {
            this.snowEffectFrameLayout.setSnowBasicCount(Integer.parseInt(snowBasicCount));
            this.snowEffectFrameLayout.setDropAverageDuration(Integer.parseInt(dropDuration));
            this.snowEffectFrameLayout.startEffect();
        }
    }
}

package com.eclairiose.triviaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.eclairiose.triviaapp.data.Repository;
import com.eclairiose.triviaapp.databinding.ActivityMainBinding;
import com.eclairiose.triviaapp.model.Questions;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    private List<Questions> list;

    private int currentQuestionIndex = 0;
private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        //setContentView( R.layout.activity_main );

        binding = DataBindingUtil.setContentView( this,R.layout.activity_main );

        list=  new Repository().getData( arrayList -> {
            binding.questionText.setText(   arrayList.get(currentQuestionIndex).getAnswer());
            upDateCounter();

        } );

        binding.buttonNext.setOnClickListener( v -> {
            currentQuestionIndex++;
            currentQuestionIndex = currentQuestionIndex%list.size();
           updataQuestion();
        } );
        binding.buttonPrev.setOnClickListener( v -> {
            if(currentQuestionIndex>0) {
                currentQuestionIndex--;
                currentQuestionIndex = currentQuestionIndex % list.size();
                updataQuestion();
            }
        } );
        binding.buttonTrue.setOnClickListener( v -> {
          Boolean Answer =  userChooseOption(true);
        } );
        binding.buttonFalse.setOnClickListener( v -> {
           Boolean Answer =  userChooseOption(false);

        } );

    }

    private Boolean userChooseOption(Boolean answer) {
        int snakeMgId=0;
        if(list.get(currentQuestionIndex).getAnswerTrue()==answer){
            snakeMgId =  R.string.AnswerCorrect;
            fadeAnimation();
            Snackbar.make( binding.cardView, snakeMgId,Snackbar.LENGTH_SHORT )
                    .show();

            return true;
        }else{
            snakeMgId=R.string.AnswerIsInCorrect;
            shakeAnimation();
            Snackbar.make( binding.cardView, snakeMgId,Snackbar.LENGTH_SHORT )
                    .show();
            return false;
        }

    }
    private void upDateCounter() {
        binding.textViewOutOf.setText( getString( R.string.text_out_off)+currentQuestionIndex+"/"+list.size() );
    }
    private void updataQuestion() {
        binding.questionText.setText( list.get(currentQuestionIndex ).getAnswer() );
        upDateCounter();
    }
    public void shakeAnimation(){

        Animation shake = AnimationUtils.loadAnimation( MainActivity.this,R.anim.shake_animation );
        binding.cardView.setAnimation(shake);
shake.setAnimationListener( new Animation.AnimationListener() {
    @SuppressLint("ResourceType")
    @Override
    public void onAnimationStart(Animation animation) {
        binding.questionText.setTextColor( Color.RED );
    }

    @SuppressLint("ResourceType")
    @Override
    public void onAnimationEnd(Animation animation) {
        binding.questionText.setTextColor( Color.WHITE);

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
       } );

    }
    private void fadeAnimation(){
        AlphaAnimation alphaAnimation = new AlphaAnimation( 0.1f,0.0f );
            alphaAnimation.setDuration(500);
            alphaAnimation.setRepeatCount( 1 );
            alphaAnimation.setRepeatMode( Animation.REVERSE );
            binding.cardView.setAnimation( alphaAnimation );

            alphaAnimation.setAnimationListener( new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    binding.questionText.setTextColor( Color.GREEN );
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    binding.questionText.setTextColor( Color.WHITE );
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            } );
        }

}
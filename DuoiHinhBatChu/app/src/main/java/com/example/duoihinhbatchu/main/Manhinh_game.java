package com.example.duoihinhbatchu.main;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duoihinhbatchu.database.MyDatabase;
import com.example.duoihinhbatchu.Question;
import com.example.duoihinhbatchu.R;
import com.example.duoihinhbatchu.view.ButtonAnswer;
import com.example.duoihinhbatchu.view.ButtonQuestion;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.String.valueOf;

public class Manhinh_game extends AppCompatActivity implements ButtonAnswer.OnItemClickedListener,ButtonQuestion.OnItemClickListener {
    MyDatabase db ;
    ImageView imageViewQuestion;
    FrameLayout frameContenner;
    Button btBoqua;
    TextView tvSuggest,tvPoint,tvExplain;

    private List<ButtonAnswer> buttonAnswerList;
    private List<ButtonQuestion> buttonQuestionList;
    private List<String> chucai = new ArrayList<>();
    // get screen man hinh

    float X = 0, Y = 0;


    int numberSuggest = 20;
    int numberPoint = 0;

    // list các câu hỏi lấy trong db
    ArrayList<Question> questionArrayList = new ArrayList<>();
    List<Question> list = new ArrayList<>();
    int id = 4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manhinh_game);
        db = new MyDatabase(this);
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        init();
        // lấy lần lượt hình ảnh
        final String s = questionArrayList.get(id).getContent();
        // run game
        runGame(id,s);

    }

    // ánh xạ
    public void init(){
        imageViewQuestion = findViewById(R.id.imageQuestion);
        frameContenner = findViewById(R.id.contener);
        btBoqua = findViewById(R.id.buttonBoqua);
        tvSuggest = findViewById(R.id.textViewSuggest);
        tvPoint = findViewById(R.id.textViewPoint);
        //lấy giá trị trong db
        for(int i=1;i<=503;i++){
            list = db.getQuestionDB(i);
            questionArrayList.addAll(list);
        }
    }
    // hàm run
    public void runGame(final int id, final String s) {
        //wordList(s);
        int imageId = getResourseId(this, s, "drawable", getPackageName());
        imageViewQuestion.setImageResource(imageId);
        // lấy kí tự trong button answer
        wordInButtonAnswer(s);
        // xử lý button trên
        buttonQuestionList = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {

            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(getResources().getDimensionPixelOffset(R.dimen.button_answer_width_height),
                    getResources().getDimensionPixelOffset(R.dimen.button_answer_width_height));
                      final ButtonQuestion buttonQuestion = new ButtonQuestion(this, this, null);
            buttonQuestion.setBackgroundResource(R.drawable.ic_anwser);

            // weight = 90*8 = 720
            if (s.length() < 8) {
                layoutParams.setMargins((int) (360 - (s.length()) * 90 / 2 + 90 * i), 20, 0, 0);
            } else {
                if (i < 8) {
                    layoutParams.setMargins((360 - (8) * 90 / 2 + 90 * i), 20, 0, 0);
                } else {
                    layoutParams.setMargins((360 - ((s.length() - 8)) * 90 / 2 + 90 * (i - 8)), 110, 0, 0);
                }
            }

            frameContenner.addView(buttonQuestion, layoutParams);
            // set cho từng button answer
            buttonQuestionList.add(buttonQuestion);
            //click
            buttonQuestion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i = 0; i < buttonAnswerList.size(); i++) {
                        if (buttonAnswerList.get(i).getButtonQuestion() == null) {

                            //buttonAnswerList.get(i).setButtonQuestion(buttonQuestion);
                            //buttonAnswerList.get(i).setText(buttonQuestion.getText());

                            //buttonQuestion.setButtonAnswer(null);
                            //buttonQuestion.setText("");

                            //buttonAnswerList.get(i).setVisibility(View.VISIBLE);
                            break;
                        }
                    }
                }
            });
        }
        // xử lý button dưới
        buttonAnswerList = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            Random rd = new Random();
            int position = rd.nextInt(s.length() - 1);
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(getResources().getDimensionPixelOffset(R.dimen.button_answer_width_height),
                    getResources().getDimensionPixelOffset(R.dimen.button_answer_width_height));
            final ButtonAnswer buttonAnswer = new ButtonAnswer(this, this, valueOf(s.charAt(position)));

            buttonAnswer.setBackgroundResource(R.drawable.ic_tile_false);
            // gan chu cho button
            if (i < s.length()) {
                buttonAnswer.setText(chucai.get(i));
            } else {
                buttonAnswer.setText(chucai.get(i));
            }
            if (i < 8) {
                layoutParams.setMargins(getResources().getDimensionPixelOffset(R.dimen.button_answer_width_height) * i, getResources().getDimensionPixelOffset(R.dimen.button_question_width_height)-40, 0, 0);
            } else {
                layoutParams.setMargins(getResources().getDimensionPixelOffset(R.dimen.button_answer_width_height) * (i - 8), getResources().getDimensionPixelOffset(R.dimen.button_question_width_height) + 90-40, 0, 0);
            }
            frameContenner.addView(buttonAnswer, layoutParams);
            buttonAnswerList.add(buttonAnswer);
            //Click
            for (int j = 0; j < buttonQuestionList.size(); j++){
                buttonQuestionList.get(j).setButtonAnswer(null);
            }

            buttonAnswer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    float X = 0, Y = 0;
                    for (int i = 0; i < buttonQuestionList.size(); i++) {
                            //buttonQuestionList.get(i).setText(buttonAnswer.getText());
                            //buttonAnswer.setButtonQuestion(null);
                            //buttonAnswer.setText(null);
                            //buttonAnswer.setVisibility(View.INVISIBLE);

                            // Animation
                            if(buttonAnswer.isFill() == false) {
                                if (buttonQuestionList.get(i).getButtonAnswer() == null) {

                                    buttonQuestionList.get(i).setButtonAnswer(buttonAnswer);
                                    buttonAnswer.setButtonQuestion(buttonQuestionList.get(i));
                                    buttonQuestionList.get(i).setText(buttonAnswer.getText());
                                    buttonAnswer.setFill(true);
                                    buttonAnswer.setIndex(i);

                                    X = -buttonAnswer.getX() + buttonQuestionList.get(i).getX();
                                    Y = -buttonAnswer.getY() +  buttonQuestionList.get(i).getY();

                                    // xử lý kết thúc
                                    String scheck = "";
                                    if (i == buttonQuestionList.size() -1){

                                        for (int j = 0; j < buttonQuestionList.size(); j++) {
                                            scheck += buttonQuestionList.get(j).getText();
                                        }
                                        if (scheck.equals(s)) {
                                            Toast.makeText(Manhinh_game.this, "Đáp án đúng", Toast.LENGTH_SHORT).show();
                                            numberPoint += 10;
                                            tvPoint.setText(numberPoint + "");
                                            btBoqua.setText("Câu Tiếp");
                                            for (int j = 0; j < buttonAnswerList.size(); j++) {
                                                if(buttonAnswerList.get(j).getButtonQuestion() != null)
                                                    buttonAnswerList.get(j).setBackgroundResource(R.drawable.ic_tile_true);
                                            }
                                            showAlertDialogExplain(id);
                                            nextPage(Manhinh_game.this);

                                        } else {
                                            scheck = "";
                                            Toast.makeText(Manhinh_game.this, "Đáp án sai! Vui lòng thử lại", Toast.LENGTH_SHORT).show();

                                        }
                                    }

                                    break;

                                }
                            }

                            else{
                                if(buttonQuestionList.get(buttonAnswer.getIndex()).getButtonAnswer() != null) {

                                    buttonAnswer.setFill(false);
                                    buttonAnswer.setButtonQuestion(null);
                                    buttonQuestionList.get(buttonAnswer.getIndex()).setButtonAnswer(null);
                                    buttonQuestionList.get(buttonAnswer.getIndex()).setText(null);

                                    break;
                                }
                            }


                    }
                    ObjectAnimator changeX = ObjectAnimator.ofFloat(buttonAnswer, "translationX", X);
                    changeX.setDuration(200L);
                    ObjectAnimator changeY = ObjectAnimator.ofFloat(buttonAnswer, "translationY", Y);
                    changeX.setDuration(200L);


                    AnimatorSet animatorSet = new AnimatorSet();
                    animatorSet.playTogether(new Animator[]{changeY,changeX});
                    animatorSet.setDuration(200L);
                    //animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());


                    animatorSet.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            if (buttonAnswer.getButtonQuestion() != null) {
                                //buttonAnswer.getButtonQuestion().setVisibility(View.INVISIBLE);
                            }

                            //buttonQuestionList.get(finalI).setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });

                    animatorSet.start();
                }
            });
        }
        //xứ lý button bỏ qua
        btBoqua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextPage(Manhinh_game.this);
            }
        });
        //xử lý button gợi ý
        xuLyButtonSuggest(s);
    }

    // phương thức lấy id hình ảnh
    public static int getResourseId(Context context, String pVariableName, String pResourcename, String pPackageName) throws RuntimeException {
        try {
            return context.getResources().getIdentifier(pVariableName, pResourcename, pPackageName);
        } catch (Exception e) {
            throw new RuntimeException("Error getting Resource ID.", e);
        }
    }


    @Override
    public void onClick(String username) {

        Toast.makeText(this, ""+username, Toast.LENGTH_SHORT).show();
    }
    public void showDialogSuggest(int id) {
        Dialog dialog = new Dialog(Manhinh_game.this);
        dialog.setTitle("Explain: ");
        tvExplain = findViewById(R.id.textViewExplain);
        tvExplain.setText(tvExplain.getText() + ": " +questionArrayList.get(id).getGiaiNghia());
        dialog.setContentView(R.layout.dialog_finish_question);
        dialog.show();
    }
    // hiển thị phần giải thích sau khi trả lời đúng
    public void showAlertDialogExplain(int id){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Explain:");
        builder.setMessage("Giải thích: "+questionArrayList.get(id).getGiaiNghia());
        builder.setCancelable(false);
        builder.setPositiveButton("Ok!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.setNegativeButton("Hiểu r", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    // xử lý button câu tiếp
    public void nextPage(final Activity act){
        btBoqua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * Intent intent = new Intent();
                 * intent.setClass(act ,act.getClass());
                act.startActivity(intent);
                act.finish();
                 */
                // lấy lần lượt hình ảnh
                frameContenner.removeAllViews();
                chucai.clear();
                id+=1;
                String str = questionArrayList.get(id).getContent();
                runGame(id,str);
                //wordList(s);

            }
        });
    }
    public boolean checkFullTextAnswer(){
        for(int i=0;i<buttonQuestionList.size();i++){
            if(buttonQuestionList.get(i).getText() != ""){
                return true;
            }
        }
        return false;
    }
    public void wordInButtonAnswer(String s){
        int k=0;
        Random c = new Random();
        String str = "ABCDEGHK" + s +"MNVPOLXCIURT";
        while (k<16){
            if(k<s.length()){
                chucai.add(valueOf(s.charAt(k)));
            }
            else{
                chucai.add(valueOf(str.charAt(c.nextInt(str.length()))));
            }
            k++;
        }
        // Đảo ngược các kí tự trong list
        int m=0;
        int n=chucai.size()-1;
        while(m<=n){
            String t = chucai.get(m);
            chucai.set(m,valueOf(str.charAt(c.nextInt(str.length()))));
            chucai.set(n,t);
            m++;
            n--;
        }
    }
    public void xuLyButtonSuggest(final String s){
        tvSuggest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(numberSuggest > 0 ){
                    //if(checkFullTextAnswer() == true){
                    // check xem cac o chu da day hay chua?
                    //}

                    numberSuggest -= 1;
                    tvSuggest.setText(String.valueOf(numberSuggest));

                    for(int i=0;i<buttonQuestionList.size();i++){
                        if(buttonQuestionList.get(i).getText() == "" ){
                            buttonQuestionList.get(i).setText(String.valueOf(s.charAt(i)));
                            buttonQuestionList.get(i).setButtonAnswer(buttonAnswerList.get(i));
                            break;
                        }
                        if(!buttonQuestionList.get(i).getText().equals(String.valueOf(s.charAt(i)) ) ){
                            buttonQuestionList.get(i).setText(String.valueOf(s.charAt(i)));
                            buttonQuestionList.get(i).setButtonAnswer(buttonAnswerList.get(i));
                            break;
                        }
                    }
                    String s1 ="";
                    for(int j=0;j<buttonQuestionList.size();j++){
                        s1 += buttonQuestionList.get(j).getText();
                    }
                    if(s1.equals(s)){
                        Toast.makeText(Manhinh_game.this, "Đáp án đúng", Toast.LENGTH_SHORT).show();
                        numberPoint +=10;
                        tvPoint.setText(numberPoint+"");
                        btBoqua.setText("Câu Tiếp");
                        showAlertDialogExplain(id);
                        nextPage(Manhinh_game.this);
                    }
                }
                if(numberSuggest == 0) {
                    Toast.makeText(Manhinh_game.this, "Hết gợi ý", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}


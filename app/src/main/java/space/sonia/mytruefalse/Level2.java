package space.sonia.mytruefalse;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class Level2 extends AppCompatActivity {

    Dialog dialog;
    public int numLeft;
    public int numRight;
    Array array = new Array();
    Random random = new Random();
    public int count = 0;//true answers

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.universal);

        //var text leveks
        TextView text_levels = findViewById(R.id.text_levels);
        text_levels.setText(R.string.level1);//text



        final ImageView imgLeft = (ImageView)findViewById(R.id.imgLeft);
        final ImageView imgRight = (ImageView)findViewById(R.id.imgRight);
//
        final TextView textLeft = findViewById(R.id.textLeft);
        final TextView textRight = findViewById(R.id.textRight);

        //corners cod




        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //dialog
        dialog =new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.previewdialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));//no color
        dialog.setCancelable(false);//нельзя закрыть окно кнопкой назад
        dialog.show();


        //cancel btn
        TextView btnclose=(TextView)dialog.findViewById(R.id.btn_close);
        btnclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ///stat
                try{
                    //вернуться на выбор уровня
                    Intent intent = new Intent(Level2.this, GameLevels.class);
                    startActivity(intent);
                    finish();//закрыть класс


                }catch(Exception e){

            }
                dialog.dismiss();//закрыть диалоговое окно

            }
        });
        //continue btn
            Button btncontinue = (Button)dialog.findViewById(R.id.buttoncontinue);
            btncontinue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();//close

                }
            });




            dialog.show();

            //btn_close
          Button btn_back=(Button)findViewById(R.id.btn_back);
          btn_back.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  try{
                      //back to levels
                      Intent intent = new Intent(Level2.this,GameLevels.class);
                      startActivity(intent);
                      finish();

                  }catch(Exception e){
              }
              }
          });


          //array fot game progress
        final int [] progress={
                R.id.point1, R.id.point2,R.id.point3, R.id.point4,
                R.id.point5, R.id.point6,R.id.point7, R.id.point8,R.id.point9,
                R.id.point10,R.id.point11, R.id.point12,R.id.point13,
                R.id.point14,R.id.point15, R.id.point16,R.id.point17,
                R.id.point18,R.id.point19, R.id.point20,};



          //animation
        final Animation a = AnimationUtils.loadAnimation(Level2.this,R.anim.alpha);
            numLeft=random.nextInt(2);
            imgLeft.setImageResource(array.images1[numLeft]);
            textLeft.setText(array.texts1[numLeft]);

            numRight = random.nextInt(2);



            while (numLeft == numRight){
                numRight = random.nextInt(2);
            }
        
            imgRight.setImageResource(array.images1[numRight]);
            textRight.setText(array.texts1[numRight]);

            //обробка нажатия на btnLeft
           imgLeft.setOnTouchListener(new View.OnTouchListener() {
               @SuppressLint("ClickableViewAccessibility")
               @Override
               public boolean onTouch(View v, MotionEvent event) {
                   //условие начала касания
                   if (event.getAction() ==MotionEvent.ACTION_DOWN){
                       //игрок не сможет сразу 2(блок)
                       imgRight.setEnabled(false);
                       if(numLeft > numRight){
                           imgLeft.setImageResource(R.drawable.true1);
                       }else {
                           imgLeft.setImageResource(R.drawable.false1);
                       }
                        //if gamer коснулся каптынки

                   }else if (event.getAction()==MotionEvent.ACTION_UP) {
                       //если отпустили касание
                       if (numLeft > numRight) {
                           if (count < 20) {
                               count = count + 1;

                           }
                           for(int i=-0; i<20;i++) {
                               TextView tv= findViewById(progress[i]);
                               tv.setBackgroundResource(R.drawable.style_points);
                           }
                           //определяем правильный answer
                           for(int i=0; i<count; i++){
                               TextView tv= findViewById(progress[i]);
                               tv.setBackgroundResource(R.drawable.style_points_green);

                           }


                       } else {
                           //false
                           if (count>0){
                               if(count==1){
                                   count=0;
                               }else{
                                   count=count-2;
                               }
                           }
                           for(int i=-0; i<19;i++) {
                               TextView tv= findViewById(progress[i]);
                               tv.setBackgroundResource(R.drawable.style_points);
                           }
                           //определяем правильный answer
                           for(int i=0; i<count; i++){
                               TextView tv= findViewById(progress[i]);
                               tv.setBackgroundResource(R.drawable.style_points_green);

                           }
                       }
                       if(count==20){
                           //levelexit
                       }else{numLeft=random.nextInt(2);
                           imgLeft.setImageResource(array.images1[numLeft]);
                           imgLeft.startAnimation(a);
                           textLeft.setText(array.texts1[numLeft]);

                           numRight = random.nextInt(2);



                           while (numLeft == numRight){
                               numRight = random.nextInt(2);
                           }

                           imgRight.setImageResource(array.images1[numRight]);
                           imgRight.startAnimation(a);
                           textRight.setText(array.texts1[numRight]);

                           imgRight.setEnabled(true);

                       }
                   }



                   return true;
               }
           });
           //btnleft finish
        ///////////////////////////
        //btnRight start
        imgRight.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //условие начала касания
                if (event.getAction() ==MotionEvent.ACTION_DOWN){
                    //игрок не сможет сразу 2(блок)
                    imgLeft.setEnabled(false); //block
                    if(numLeft < numRight){
                        imgRight.setImageResource(R.drawable.true1);
                    }else {
                        imgRight.setImageResource(R.drawable.false1);
                    }
                    //if gamer коснулся каптынки

                }else if (event.getAction()==MotionEvent.ACTION_UP) {
                    //если отпустили касание
                    if (numLeft < numRight) {
                        if (count < 20) {
                            count = count + 1;

                        }
                        for(int i=-0; i<20;i++) {
                            TextView tv= findViewById(progress[i]);
                            tv.setBackgroundResource(R.drawable.style_points);
                        }
                        //определяем правильный answer
                        for(int i=0; i<count; i++){
                            TextView tv= findViewById(progress[i]);
                            tv.setBackgroundResource(R.drawable.style_points_green);

                        }


                    } else {
                        //false
                        if (count>0){
                            if(count==1){
                                count=0;
                            }else{
                                count=count-2;
                            }
                        }
                        for(int i=-0; i<19;i++) {
                            TextView tv= findViewById(progress[i]);
                            tv.setBackgroundResource(R.drawable.style_points);
                        }
                        //определяем правильный answer
                        for(int i=0; i<count; i++){
                            TextView tv= findViewById(progress[i]);
                            tv.setBackgroundResource(R.drawable.style_points_green);

                        }
                    }
                    if(count==20){
                        //levelexit
                    }else{numLeft=random.nextInt(2);
                        imgLeft.setImageResource(array.images1[numLeft]);
                        imgLeft.startAnimation(a);
                        textLeft.setText(array.texts1[numLeft]);

                        numRight = random.nextInt(2);



                        while (numLeft == numRight){
                            numRight = random.nextInt(2);
                        }

                        imgRight.setImageResource(array.images1[numRight]);
                        imgRight.startAnimation(a);
                        textRight.setText(array.texts1[numRight]);

                        imgLeft.setEnabled(true);

                    }
                }



                return true;
            }
        });
        /////////////////
        //btnRight finish



    }
    //system btn back

    @Override
    public void onBackPressed(){
        try{
            //back to levels
            Intent intent = new Intent(Level2.this,GameLevels.class);
            startActivity(intent);
            finish();

        }catch(Exception e){
        }

    }



}

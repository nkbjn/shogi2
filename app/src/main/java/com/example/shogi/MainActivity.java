package com.example.shogi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.*;
import android.app.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;

public class MainActivity extends AppCompatActivity {
    TextView tv;
    String coma[] = {"玉", "金", "銀", "角", "飛", "歩", "全", "馬", "龍", "と", " "};
    String te[] = {"▼", "△"};
    String nari[] = {"成っちゃう", "成らない"};
    int start = -1, goal = -1;
    int jyoutai[][] = {{4, 2}, {3, 2}, {2, 2}, {1, 2}, {0, 2},
            {10, 0}, {10, 0}, {10, 0}, {10, 0}, {5, 2},
            {10, 0}, {10, 0}, {10, 0}, {10, 0}, {10, 0},
            {5, 1}, {10, 0}, {10, 0}, {10, 0}, {10, 0},
            {0, 1}, {1, 1}, {2, 1}, {3, 1}, {4, 1}};
    int jyoutai_bt1[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    int jyoutai_bt5[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    int jyun = 1;
    int moti[] = {0, 0};
    Button[] bt1 = new Button[10];
    Button[] bt2 = new Button[2];
    Button[][] bt3 = new Button[5][5];
    Button[] bt4 = new Button[2];
    Button[] bt5 = new Button[10];
    int possible = 0;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        setContentView(ll);

        tv = new TextView(this);
        tv.setText("先手どうぞ");
        tv.setTextSize(40);

        TableLayout tl1 = new TableLayout(this);
        TableRow[] tr1 = new TableRow[1];

        TableLayout tl2 = new TableLayout(this);
        TableRow[] tr2 = new TableRow[1];

        TableLayout tl3 = new TableLayout(this);
        TableRow[] tr3 = new TableRow[5];

        TableLayout tl4 = new TableLayout(this);
        TableRow[] tr4 = new TableRow[1];

        TableLayout tl5 = new TableLayout(this);
        TableRow[] tr5 = new TableRow[1];


        for (int i = 0; i < tr1.length; i++) tr1[i] = new TableRow(this);
        for (int i = 0; i < tr2.length; i++) tr2[i] = new TableRow(this);
        for (int i = 0; i < tr3.length; i++) tr3[i] = new TableRow(this);
        for (int i = 0; i < tr4.length; i++) tr4[i] = new TableRow(this);
        for (int i = 0; i < tr5.length; i++) tr5[i] = new TableRow(this);

        for (int i = 0; i < bt1.length; i++) bt1[i] = new Button(this);
        for (int i = 0; i < bt2.length; i++) bt2[i] = new Button(this);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                bt3[i][j] = new Button(this);
            }
        }
        for (int i = 0; i < bt4.length; i++) bt4[i] = new Button(this);
        for (int i = 0; i < bt5.length; i++) bt5[i] = new Button(this);

        for (int i = 0; i < 5; i += 4) {
            for (int j = 0; j < 5; j++) {
                if (i == 0) bt3[i][j].setText(te[0] + coma[4 - j]);
                else bt3[i][j].setText(te[1] + coma[j]);
            }
        }
        bt3[1][4].setText(te[0] + coma[5]);
        bt3[3][0].setText(te[1] + coma[5]);

        bt2[0].setText(nari[0]);
        bt2[1].setText(nari[1]);

        bt4[0].setText(nari[0]);
        bt4[1].setText(nari[1]);

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                tr3[i].addView(bt3[i][j]);
                bt3[i][j].setId(i * 5 + j);
            }
            tl3.addView(tr3[i]);
        }
        for (int i = 0; i < bt1.length; i++) {
            tr1[0].addView(bt1[i]);
            bt1[i].setId(i + 25);
        }
        for (int i = 0; i < bt2.length; i++) tr2[0].addView(bt2[i]);
        for (int i = 0; i < bt4.length; i++) tr4[0].addView(bt4[i]);
        for (int i = 0; i < bt5.length; i++) {
            tr5[0].addView(bt5[i]);
            bt5[i].setId(i + 35);
        }

        ll.addView(tv);

        tl1.addView(tr1[0]);
        ll.addView(tl1);

        tl2.addView(tr2[0]);
        ll.addView(tl2);

        ll.addView(tl3);

        tl4.addView(tr4[0]);
        ll.addView(tl4);

        tl5.addView(tr5[0]);
        ll.addView(tl5);
        SampleClickListener scl = new SampleClickListener();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) bt3[i][j].setOnClickListener(scl);
        }
        for (int i = 0; i < 10; i++) {
            bt1[i].setOnClickListener(scl);
            bt5[i].setOnClickListener(scl);
        }
    }

    class SampleClickListener implements OnClickListener {

        public void onClick(View v) {
            if (v.getId() < 25) {
                if (start == -1) {
                    if (jyoutai[v.getId()][1] == jyun) {
                        start = v.getId();
                        tv.setText("どこに動かす？1");
                    } else tv.setText("そこは選べません2");
                } else {
                    if (start < 25) {
                        if (jyoutai[v.getId()][1] != jyun && v.getId() < 25) {
                            goal = v.getId();
                        } else {
                            tv.setText("そこは選べません3");
                            start = -1;
                            goal=-1;
                        }
                    } else {
                        if (jyoutai[v.getId()][1] == 0) {
                            goal = v.getId();
                        } else {
                            tv.setText("そこは選べません5");
                            start = -1;
                            goal=-1;
                        }
                    }
                }
            } else if (jyun == 2 && start == -1 && jyoutai_bt1[v.getId() - 25] != 0) {
                start = v.getId();
                tv.setText("どこに動かす？2");
            } else{
                if (jyun == 1 && start == -1 && jyoutai_bt5[v.getId() - 35] != 0) {
                    start = v.getId();
                    tv.setText("どこに動かす？3");
                } else {
                    tv.setText("そこはえらべません4");
                    start = -1;
                }
            }

            if (start != -1 && goal != -1) {
                if (start < 25) possible = possible(jyoutai[start][0]);
                else possible = 1;

                if (possible == 1) {
                    if (jyun == 1) sente();
                    else gote();
                    if (jyun == 1) {
                        jyun = 2;
                        tv.setText("後手どうぞ");
                    } else {
                        jyun = 1;
                        tv.setText("先手どうぞ");
                    }
                } else{
                    tv.setText("そこは選べません3");
                    start=-1;
                    goal=-1;
                }
                possible=0;
            }
        }

    }

    private void sente() {
        if (jyoutai[goal][0] != 10) {
            bt5[moti[0]].setText(te[1] + coma[jyoutai[goal][0]]);
            if (jyoutai[goal][0] < 6) jyoutai_bt5[moti[0]] = jyoutai[goal][0];
            else jyoutai_bt5[moti[0]] = jyoutai[goal][0] - 4;
            moti[0]++;
        }
        if (start < 25) {
            bt3[goal / 5][goal % 5].setText(te[1] + coma[jyoutai[start][0]]);
            bt3[start / 5][start % 5].setText("");
            jyoutai[start][1] = 0;
            jyoutai[goal][1] = 1;
            jyoutai[goal][0] = jyoutai[start][0];
            jyoutai[start][0] = 10;
            start = -1;
            goal = -1;
        } else {
            bt3[goal / 5][goal % 5].setText(te[1] + coma[jyoutai_bt5[start - 35]]);
            bt5[start - 35].setText("");
            jyoutai[goal][1] = 1;
            jyoutai[goal][0] = jyoutai_bt5[start - 35];
            jyoutai_bt5[start - 35] = 0;
            start = -1;
            goal = -1;
        }
    }

    public void gote() {
        if (jyoutai[goal][0] != 10) {
            bt1[moti[1]].setText(te[0] + coma[jyoutai[goal][0]]);
            if (jyoutai[goal][0] < 6) jyoutai_bt1[moti[1]] = jyoutai[goal][0];
            else jyoutai_bt1[moti[1]] = jyoutai[goal][0] - 4;
            moti[1]++;
        }
        if (start < 25) {
            bt3[goal / 5][goal % 5].setText(te[0] + coma[jyoutai[start][0]]);
            bt3[start / 5][start % 5].setText("");
            jyoutai[start][1] = 0;
            jyoutai[goal][1] = 2;
            jyoutai[goal][0] = jyoutai[start][0];
            jyoutai[start][0] = 10;
            start = -1;
            goal = -1;
        } else {
            bt3[goal / 5][goal % 5].setText(te[0] + coma[jyoutai_bt1[start - 25]]);
            bt1[start - 25].setText("");
            jyoutai[goal][1] = 2;
            jyoutai[goal][0] = jyoutai_bt1[start - 25];
            jyoutai_bt1[start - 25] = 0;
            start = -1;
            goal = -1;
        }
    }

    public int possible(int a) {
        switch (a) {
            case 0:
                return ou();
            case 1:
                return kin();
            case 2:
                return gin();
            case 3:
                return kaku();
            case 4:
                return hisha();
            case 5:
                return hu();
            case 6:
                return kin();
            case 7:
                return ou()+kaku();
            case 8:
                return ou()+hisha();
            case 9:
                return kin();
            default:
                return 0;
        }
    }

    public int ou() {
        int a = start / 5, b = goal / 5, c = start % 5, d = goal % 5;
        if (Math.abs(a - b) <= 1 && Math.abs(c - d) <= 1) return 1;
        return 0;
    }

    public int kin() {
        int a = start / 5, b = goal / 5, c = start % 5, d = goal % 5;
        if (jyun == 1) {
            if (Math.abs(a - b) <= 1 && Math.abs(c - d) <= 1) {
                if(b-a==1 && c!=d )return 0;
                return 1;
            } else return 0;
        }
        else{
            if(Math.abs(a - b) <= 1 && Math.abs(c - d) <= 1){
                if(a-b==1 && c!=d) return 0;
                return 1;
            }else return 0;
        }
    }

    public int gin(){
        int a = start / 5, b = goal / 5, c = start % 5, d = goal % 5;
        if (jyun == 1) {
            if (Math.abs(a - b) <= 1 && Math.abs(c - d) <= 1) {
                if(b==a || b-a==1 )return 0;
                return 1;
            } else return 0;
        }
        else{
            if(Math.abs(a - b) <= 1 && Math.abs(c - d) <= 1){
                if(a==b || a-b==1) return 0;
                return 1;
            }else return 0;
        }
    }
    public int kaku(){
        int a = start / 5, b = goal / 5, c = start % 5, d = goal % 5;
        if(Math.abs(a-b)==Math.abs(c-d)){
            int e=Math.abs(a-b);
            if(a-b>0){
                if(c-d>0){
                    for(int i=1;i<e;i++) if(jyoutai[start-6*i][0]!=10) return 0;
                    return 1;
                }else{
                    for(int i=1;i<e;i++) if(jyoutai[start-4*i][0]!=10) return 0;
                    return 1;
                }
            }else{
                if(c-d>0){
                    for(int i=1;i<e;i++) if(jyoutai[start+4*i][0]!=10) return 0;
                    return 1;
                }else{
                    for(int i=1;i<e;i++) if(jyoutai[start+6*i][0]!=10) return 0;
                    return 1;
                }
            }
        }else return 0;
    }
    public int hisha(){
        int a = start / 5, b = goal / 5, c = start % 5, d = goal % 5,e;
        if(a== b || c==d){
            if(a==b){
                e=Math.abs(c-d);
                if(c-d>0){
                    for(int i=1;i<e;i++)if(jyoutai[start+i][0]!=10)return 0;
                    return 1;
                }else{
                    for(int i=1;i<e;i++)if(jyoutai[start+i][0]!=10)return 0;
                    return 1;
                }
            }else{
                e=Math.abs(a-b);
                if(a-b>0){
                    for(int i=1;i<e;i++) if(jyoutai[start-5*i][0]!=10)return 0;
                    return 1;
                }
                else{
                    for(int i=1;i<e;i++)if(jyoutai[start+5*i][0]!=10)return 0;
                    return 1;
                }
            }
        }else return 0;
    }
    public int hu() {
        int a = start / 5, b = goal / 5, c = start % 5, d = goal % 5;
        if(c==d){
            if(jyun==1){
                if(a-b==1)return 1;
                else return 0;
            }else{
                if(b-a==1)return 1;
                else return 0;
            }
        }else return 0;
    }

}


//{"玉", "金", "銀", "角", "飛", "歩", "全", "馬", "龍", "と"," "};


// 課題番号 6-3
// 氏名　(中部 仁)
// 学籍番号 (1520142)

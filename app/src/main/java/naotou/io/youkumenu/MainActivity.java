package naotou.io.youkumenu;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    private RelativeLayout menu1;
    private RelativeLayout menu2;
    private RelativeLayout menu3;

    private boolean isMenu1Show = true;
    private boolean isMenu2Show = true;
    private boolean isMenu3Show = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        menu1 = (RelativeLayout) findViewById(R.id.menu1);
        menu2 = (RelativeLayout) findViewById(R.id.menu2);
        menu3 = (RelativeLayout) findViewById(R.id.menu3);
        findViewById(R.id.home_1).setOnClickListener(this);
        findViewById(R.id.home_2).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_1://当点击房子时
                //判断二级菜单是否显示
                if (isMenu2Show) {
                    //如果二级是显示的
                    hideView(menu2, 100);
                    isMenu2Show = false;
                    if (isMenu3Show) {
                        //如果三级是显示的
                        hideView(menu3, 0);
                        isMenu3Show = false;
                    }
                } else {
                    //如果二级是隐藏的
                    showView(menu2);
                    isMenu2Show = true;
                }
                break;
            case R.id.home_2://点击二层时
                //判断三级菜单的状态,
                if (isMenu3Show) {
                    //如果三级是显示的
                    hideView(menu3, 0);
                    isMenu3Show = false;
                } else {
                    //如果三级是隐藏的
                    showView(menu3);
                    isMenu3Show = true;
                }
                break;

        }
    }

    private void showView(RelativeLayout layout) {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.rotale_d);
        animation.setFillAfter(true);
        layout.startAnimation(animation);

        for (int j = 0; j < layout.getChildCount(); j++) {
            View child = layout.getChildAt(j);
            child.setEnabled(true);
        }

    }


    private void hideView(RelativeLayout layout, int i) {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.rotale);
        animation.setStartOffset(i);
        animation.setFillAfter(true);
        layout.startAnimation(animation);
        //解决如果隐藏了二级view 在相同位置继续点击会相应点击事件的问题.
        for (int j = 0; j < layout.getChildCount(); j++) {
            View child = layout.getChildAt(j);
            child.setEnabled(false);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            if (!isMenu1Show) {
                //如果一级菜单是隐藏的,那么显示12级
                showView(menu1);
                showView(menu2);
                isMenu1Show = true;
                isMenu2Show = true;
            } else {
                //如果一级菜单是显示的,隐藏一级菜单
                hideView(menu1, 0);
                isMenu1Show = false;
                if (isMenu2Show) {
                    hideView(menu2, 100);
                    isMenu2Show = false;
                    if (isMenu3Show) {
                        hideView(menu3, 200);
                        isMenu3Show = false;
                    }
                }
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}

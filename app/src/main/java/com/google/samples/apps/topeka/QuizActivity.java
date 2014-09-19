/*
 * Copyright 2014 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.samples.apps.topeka;

import com.google.samples.apps.topeka.model.Category;
import com.google.samples.apps.topeka.model.Theme;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import static com.google.samples.apps.topeka.adapter.CategoryAdapter.ICON_CATEGORY;
import static com.google.samples.apps.topeka.adapter.CategoryAdapter.DRAWABLE;

public class QuizActivity extends ActionBarActivity implements View.OnClickListener {

    public static final String BACKGROUND = "_background";
    public static final String PRIMARY = "_primary";
    public static final String FOREGROUND = "_foreground";

    private static final String TAG = "QuizActivity";
    private static final String THEME = "theme_";
    private static final String COLOR = "color";
    private static final String IMAGE_CATEGORY = "image_category_";

    public static Intent getStartIntent(Context context, Category category) {
        Intent starter = new Intent(context, QuizActivity.class);
        starter.putExtra(Category.TAG, category);
        return starter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        populate((Category) getIntent().getParcelableExtra(Category.TAG));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start_quiz:
                //TOTO replace with real action
                Toast.makeText(this, "I'm sorry Dave, I can't let you do this.", Toast.LENGTH_LONG)
                        .show();
                break;
            default:
                throw new IllegalArgumentException(
                        "OnClick has not been implemented for " + getResources()
                                .getResourceName(v.getId()));

        }
    }

    private void populate(Category category) {
        initLayout(category.getId());
        setTheme(category.getTheme());
        setTitle(category.getName());
    }

    private void initLayout(String categoryId) {
        setContentView(R.layout.activity_quiz);

        ImageView icon = (ImageView) findViewById(R.id.icon);
        icon.setImageResource(getResources().getIdentifier(IMAGE_CATEGORY + categoryId, DRAWABLE,
                getApplicationContext().getPackageName()));

        ImageView btnStartQuiz = (ImageView) findViewById(R.id.btn_start_quiz);
        btnStartQuiz.setOnClickListener(this);
    }

    private void setTheme(Theme theme) {

        if (null == theme) {
            Log.d(TAG, "No theme found. Falling back to default");
            theme = Theme.topeka;
        }
        //TODO find a way to set the theme without string matching

        int colorBackground = getColor(theme, BACKGROUND);
        int colorPrimary = getColor(theme, PRIMARY);
        int colorForeground = getColor(theme, FOREGROUND);

        findViewById(R.id.quiz_container).setBackgroundColor(colorBackground);

        setStatusAndNavigationBarColor(colorPrimary);
        setActionBarColor(colorPrimary);
        setToolbarColor(colorForeground);

        setTheme(theme.getResId());
        Log.d(TAG, "Current theme is" + getResources().getResourceName(theme.getResId()));
    }

    private int getColor(Theme theme, String colorType) {
        Resources resources = getResources();
        int resourceId = resources
                .getIdentifier(THEME + theme.name() + colorType, COLOR,
                        getApplicationContext().getPackageName());
        return resources.getColor(resourceId);
    }

    @TargetApi(Build.VERSION_CODES.L)
    private void setStatusAndNavigationBarColor(int colorPrimary) {
        if (Build.VERSION_CODES.KITKAT < Build.VERSION.SDK_INT) {
            getWindow().setStatusBarColor(colorPrimary);
            getWindow().setNavigationBarColor(colorPrimary);
        }
    }

    private void setActionBarColor(int colorPrimary) {
        ActionBar actionBar = getSupportActionBar();
        if (null != actionBar) {
            actionBar.setBackgroundDrawable(new ColorDrawable(colorPrimary));
        }
    }

    private void setToolbarColor(int colorForeground) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.action_bar);
        if (null != toolbar) {
            toolbar.setTitleTextColor(colorForeground);
        }
    }

}
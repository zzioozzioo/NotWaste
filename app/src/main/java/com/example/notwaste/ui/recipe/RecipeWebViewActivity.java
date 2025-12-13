package com.example.notwaste.ui.recipe;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.notwaste.R;
import com.example.notwaste.ui.fridge.FridgeActivity;
import com.example.notwaste.ui.home.HomeActivity;
import com.example.notwaste.ui.mypage.MyPageActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class RecipeWebViewActivity extends AppCompatActivity {

    EditText editSearch;
    Button btnSearch;
    WebView webRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_webview);

        editSearch = findViewById(R.id.editSearch);
        btnSearch = findViewById(R.id.btnSearch);
        webRecipe = findViewById(R.id.webRecipe);

        // WebView 설정
        WebSettings settings = webRecipe.getSettings();
        settings.setJavaScriptEnabled(true);
        webRecipe.setWebViewClient(new WebViewClient());

        // 기본 페이지
//        webRecipe.loadUrl("https://www.google.com/search?q=레시피");

        // 검색 버튼 클릭 시
        btnSearch.setOnClickListener(v -> searchRecipe());

        // 키보드 엔터 검색
        editSearch.setOnEditorActionListener((v, actionId, event) -> {
            searchRecipe();
            return true;
        });

        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigation);

// 현재 화면이 레시피이므로 선택 상태 고정
        bottomNav.setSelectedItemId(R.id.menu_recipe);

        bottomNav.setOnItemSelectedListener(item -> {

            if (item.getItemId() == R.id.menu_home) {
                startActivity(new Intent(this, HomeActivity.class));
                return true;

            } else if (item.getItemId() == R.id.menu_fridge) {
                startActivity(new Intent(this, FridgeActivity.class));
                return true;

            } else if (item.getItemId() == R.id.menu_mypage) {
                startActivity(new Intent(this, MyPageActivity.class));
                return true;

            } else if (item.getItemId() == R.id.menu_recipe) {
                // 이미 현재 화면 → 아무것도 안 함
                return true;
            }

            return false;
        });

    }

    private void searchRecipe() {
        String keyword = editSearch.getText().toString();

        if (keyword.isEmpty()) return;

        // 구글 레시피 검색 URL
        String url = "https://www.google.com/search?q=" + keyword + "+레시피";

        webRecipe.loadUrl(url);
    }

    @Override
    public void onBackPressed() {
        // WebView 뒤로가기 기능
        if (webRecipe.canGoBack()) {
            webRecipe.goBack();
        } else {
            super.onBackPressed();
        }
    }


}

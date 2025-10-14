package com.purpura.app.ui.screens.accountFeatures;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.purpura.app.ui.screens.MyPurchasesFragment;
import com.purpura.app.ui.screens.MySalesFragment;
import com.purpura.app.R;
import com.purpura.app.configuration.Methods;
import com.purpura.app.ui.screens.errors.InternetError;

public class MyOrders extends AppCompatActivity {


    private String[] labels = new String[]{"Compras", "Vendas"};
    private MyOrdersAdapter adapter;
    Methods methods = new Methods();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_my_orders);

        ImageView backButton = findViewById(R.id.myOrdersBackButton);
        backButton.setOnClickListener(v -> finish());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TabLayout myOrdersTableLayout = findViewById(R.id.myOrdersTabLayout);
        ViewPager2 myOrdersViewPage = findViewById(R.id.myOrdersViewPage);
        adapter = new MyOrdersAdapter(this);
        myOrdersViewPage.setAdapter(adapter);

        MyOrdersAdapter myOrdersAdapter = new MyOrdersAdapter(this);

        new TabLayoutMediator(myOrdersTableLayout, myOrdersViewPage,
                (tab, position) -> tab.setText(labels[position])
        ).attach();

    }

    public class MyOrdersAdapter extends FragmentStateAdapter {

        public MyOrdersAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return new MyPurchasesFragment();
                case 1:
                    return new MySalesFragment();
            }
            return new MyPurchasesFragment();
        }

        @Override
        public int getItemCount() {
            return labels.length;
        }
    }
}



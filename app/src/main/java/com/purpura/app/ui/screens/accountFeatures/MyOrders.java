package com.purpura.app.ui.screens.accountFeatures;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.purpura.app.R;
import com.purpura.app.configuration.Methods;
import com.purpura.app.ui.account.AccountFragment;

public class MyOrders extends AppCompatActivity {

    Methods methods = new Methods();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_my_orders);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Views
        View purchaseView = findViewById(R.id.purchaseView);
        View salesView = findViewById(R.id.salesView);
        TextView purchaseText = findViewById(R.id.purchaseText);
        TextView salesText = findViewById(R.id.salesText);
        RecyclerView purchaseRecyclerView = findViewById(R.id.purchaseRecycleView);
        RecyclerView salesRecyclerView = findViewById(R.id.salesRecycleView);
        ImageView backButton = findViewById(R.id.myOrdersBackButton);

        View.OnClickListener openPurchases = v -> {
            purchaseRecyclerView.setVisibility(VISIBLE);
            purchaseRecyclerView.setBackgroundColor(Color.GREEN);
            salesRecyclerView.setVisibility(INVISIBLE);
        };
        purchaseView.setOnClickListener(openPurchases);
        purchaseText.setOnClickListener(openPurchases);

        View.OnClickListener openSales = v -> {
            salesRecyclerView.setVisibility(VISIBLE);
            salesRecyclerView.setBackgroundColor(Color.RED);
            purchaseRecyclerView.setVisibility(INVISIBLE);
        };
        salesView.setOnClickListener(openSales);
        salesText.setOnClickListener(openSales);

        purchaseRecyclerView.setVisibility(VISIBLE);
        salesRecyclerView.setVisibility(INVISIBLE);

        backButton.setOnClickListener(v -> {
            methods.openScreenActivity(this, AccountFragment.class);
        });
    }
}

package com.softmeth.project5;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Initializes the activity, setting up the user interface from a layout resource,
 * and assigns click listeners to the ImageView objects that start different activities
 * in the application.
 *
 * @author Ridwan Sharkar
 */
public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView orderCoffeeImageView = findViewById(R.id.OrderCoffeeImageView);
        orderCoffeeImageView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, OrderCoffeeActivity.class);
                startActivity(intent);
            }
        });
        ImageView OrderDonutImageView = findViewById(R.id.OrderDonutImageView);
        OrderDonutImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, OrderDonutActivity.class);
                startActivity(intent);
            }
        });
        ImageView OrderSandwichImageView = findViewById(R.id.OrderSandwichImageView);
        OrderSandwichImageView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, OrderSandwichActivity.class);
                startActivity(intent);
            }
        });
        ImageView CurrentOrderImageView = findViewById(R.id.CurrentOrderImageView);
        CurrentOrderImageView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, CurrentOrderActivity.class);
                startActivity(intent);
            }
        });
        ImageView AllOrdersImageView = findViewById(R.id.AllOrdersImageView);
        AllOrdersImageView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, AllOrdersActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Invoked when the activity is becoming visible to the user.
     * This method logs the execution of onStart to the console for debugging purposes.
     */
    protected void onStart()
    {
        super.onStart();
        System.out.println("onStart() executed");
    }

    /**
     * A callback method executed right after onStart()
     */
    protected void onResume() {
        super.onResume();
        System.out.println("onResume() executed");
    }
}



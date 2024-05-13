package com.softmeth.project5;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Activity for managing and displaying the current order.
 * Allows users to view their order details, remove items, and place the final order.
 *
 * @author Ridwan Sharkar
 */
public class CurrentOrderActivity extends AppCompatActivity
{
    private TextView subtotalTextView;
    private TextView taxTextView;
    private TextView totalTextView;
    private ListView orderListView;
    private Button removeSelectedItemButton;
    private Button placeOrderButton;

    /**
     * Initializes the activity, sets up user interface interactions, and prepares the order display.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_current_order);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        orderListView = findViewById(R.id.orderListView);
        subtotalTextView = findViewById(R.id.subtotalTextView);
        taxTextView = findViewById(R.id.taxTextView);
        totalTextView = findViewById(R.id.totalTextView);
        removeSelectedItemButton = findViewById(R.id.RemoveSelectedItemButton);
        placeOrderButton = findViewById(R.id.PlaceOrderButton);
        Order currentOrder = Order.getInstance();
        ListView orderListView = findViewById(R.id.orderListView);
        orderListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        ArrayAdapter<MenuItem> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_activated_1,
                currentOrder.getItems());
        orderListView.setAdapter(adapter);
        updateCosts(subtotalTextView, taxTextView, totalTextView, currentOrder);
        setupButtonListeners(removeSelectedItemButton, placeOrderButton, currentOrder, adapter);
    }

    /**
     * Updates the displayed costs for subtotal, tax, and total based on the current order.
     * Assumes a tax rate of 7%.
     *
     * @param subtotalTextView TextView for displaying the subtotal.
     * @param taxTextView TextView for displaying the tax.
     * @param totalTextView TextView for displaying the total.
     * @param currentOrder The current order from which to calculate costs.
     */
    private void updateCosts(TextView subtotalTextView, TextView taxTextView,
                             TextView totalTextView, Order currentOrder) {
        double subtotal = currentOrder.calculateTotal();
        double tax = subtotal * 0.07; // Assume a 7% tax rate
        double total = subtotal + tax;

        subtotalTextView.setText(String.format("$%.2f", subtotal));
        taxTextView.setText(String.format("$%.2f", tax));
        totalTextView.setText(String.format("$%.2f", total));
    }

    /**
     * Sets up listeners for the 'Remove Selected Item' and 'Place Order' buttons.
     */
    private void setupButtonListeners(Button removeSelectedItemButton, Button placeOrderButton,
                                      Order currentOrder, ArrayAdapter<MenuItem> adapter) {
        removeSelectedItemButton.setOnClickListener(v -> {
            int position = orderListView.getCheckedItemPosition();
            if (position != ListView.INVALID_POSITION) {
                MenuItem itemToRemove = adapter.getItem(position);
                if (itemToRemove != null) {
                    currentOrder.removeItem(itemToRemove);
                    adapter.remove(itemToRemove);
                    adapter.notifyDataSetChanged();
                    updateCosts(subtotalTextView, taxTextView, totalTextView, currentOrder);
                }
                orderListView.setItemChecked(position, false);
                Toast.makeText(CurrentOrderActivity.this, "Item removed from order", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(CurrentOrderActivity.this, "Select an item to remove", Toast.LENGTH_SHORT).show();
            }
        });
        placeOrderButton.setOnClickListener(v -> {
            if (!currentOrder.getItems().isEmpty()) {
                ArrayList<MenuItem> orderToSave = new ArrayList<>(currentOrder.getItems());
                ((MyApplication)getApplication()).getAllOrders().add(orderToSave);
                currentOrder.resetOrder();
                adapter.clear();
                adapter.notifyDataSetChanged();
                updateCosts(subtotalTextView, taxTextView, totalTextView, currentOrder);
                Toast.makeText(CurrentOrderActivity.this, "Order placed and saved!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(CurrentOrderActivity.this, "Add items to your order first", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
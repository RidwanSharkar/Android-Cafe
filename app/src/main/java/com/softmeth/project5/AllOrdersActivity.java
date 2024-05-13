package com.softmeth.project5;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

/**
 * Activity to display all placed orders within the app.
 * Users can view details of each order, including total amounts, and have the option to cancel any order.
 *
 * @author Ridwan Sharkar
 */
public class AllOrdersActivity extends AppCompatActivity {
    private Spinner ordersSpinner;
    private ListView orderDetailsListView;
    private TextView totalAmountTextView;
    private Button cancelOrderButton;
    private ArrayAdapter<String> orderNamesAdapter;
    private ArrayAdapter<String> orderDetailsAdapter;

    /**
     * Called when the activity is first created.
     * Initializes the views, sets up the orders spinner, and configures the cancel button listener.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_orders);

        initializeViews();
        setupOrderSpinner();
        setupCancelButtonListener();
    }

    /**
     * Initializes the user interface elements from the XML layout.
     * Sets up adapters for the ListView and Spinner that will hold the order details and list of orders, respectively.
     */
    private void initializeViews() {
        ordersSpinner = findViewById(R.id.ordersSpinner);
        orderDetailsListView = findViewById(R.id.orderDetailsListView);
        totalAmountTextView = findViewById(R.id.TotalAmountTextView);
        cancelOrderButton = findViewById(R.id.cancelOrderButton);
        orderDetailsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>());
        orderDetailsListView.setAdapter(orderDetailsAdapter);
    }

    /**
     * Configures the spinner with the list of all orders. Sets up an onItemSelectedListener to handle selection events.
     * When an order is selected, its details are displayed in the ListView and the total amount is calculated and displayed.
     */
    private void setupOrderSpinner() {
        MyApplication appInstance = (MyApplication) getApplicationContext();
        ArrayList<ArrayList<MenuItem>> allOrders = appInstance.getAllOrders();
        ArrayList<String> orderNames = new ArrayList<>();
        for (int i = 0; i < allOrders.size(); i++) {
            orderNames.add("Order " + (i + 1));
        }
        orderNamesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, orderNames);
        ordersSpinner.setAdapter(orderNamesAdapter);
        ordersSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<MenuItem> selectedOrder = allOrders.get(position);
                ArrayList<String> orderDetails = new ArrayList<>();
                for (MenuItem item : selectedOrder) {
                    orderDetails.add(item.toString());}
                orderDetailsAdapter.clear();
                orderDetailsAdapter.addAll(orderDetails);
                orderDetailsAdapter.notifyDataSetChanged();
                double total = 0.0;
                for (MenuItem item : selectedOrder) {
                    total += item.price();}
                totalAmountTextView.setText(String.format("$%.2f", total));}
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                orderDetailsAdapter.clear();
                orderDetailsAdapter.notifyDataSetChanged();
                totalAmountTextView.setText("$0.00");
            }});
    }

    /**
     * Sets up the click listener for the cancel order button. Removes the selected order when clicked.
     */
    private void setupCancelButtonListener() {
        cancelOrderButton.setOnClickListener(v -> {
            int selectedPosition = ordersSpinner.getSelectedItemPosition();
            if (selectedPosition != AdapterView.INVALID_POSITION) {
                MyApplication appInstance = (MyApplication) getApplicationContext();
                ArrayList<ArrayList<MenuItem>> allOrders = appInstance.getAllOrders();
                allOrders.remove(selectedPosition);
                orderNamesAdapter.remove(orderNamesAdapter.getItem(selectedPosition));
                orderNamesAdapter.notifyDataSetChanged();
                orderDetailsAdapter.clear();
                orderDetailsAdapter.notifyDataSetChanged();
                totalAmountTextView.setText("$0.00");
            }
        });
    }
}
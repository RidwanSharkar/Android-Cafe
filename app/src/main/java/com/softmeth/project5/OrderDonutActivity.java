package com.softmeth.project5;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import android.widget.ImageView;

/**
 * Activity for ordering donuts in the RU Café system.
 * Allows users to select donut types and quantities, view pricing, and add selections to their order.
 *
 * @author Ridwan Sharkar
 */
public class OrderDonutActivity extends AppCompatActivity
{
    private Spinner QuantityDonutSpinner;
    private TextView SubtotalDonutTextView;
    private Button AddToOrderDonutButton;
    private RecyclerView recyclerView;
    private DonutAdapter adapter;
    private ArrayList<DonutItem> donuts;
    private ImageView DonutImageView;
    private int lastSelectedIndex = -1;

    /**
     * Called when the activity is starting. This is where most initialization should go.
     * Initializes the activity's UI and sets up the necessary adapters and listeners.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down, this contains the most recent data.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_donut);
        DonutImageView = findViewById(R.id.DonutImageView);
        QuantityDonutSpinner = findViewById(R.id.QuantityDonutSpinner);
        SubtotalDonutTextView = findViewById(R.id.SubtotalDonutTextView);
        AddToOrderDonutButton = findViewById(R.id.AddToOrderDonutButton);

        ArrayAdapter<CharSequence> quantityAdapter = ArrayAdapter.createFromResource(this,
                R.array.quantities, android.R.layout.simple_spinner_item);
        quantityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        QuantityDonutSpinner.setAdapter(quantityAdapter);
        QuantityDonutSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (lastSelectedIndex >= 0) updateSubtotal();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        SubtotalDonutTextView.setText("$0.00");
        AddToOrderDonutButton.setOnClickListener(v -> {
            if (lastSelectedIndex == -1) {
                Toast.makeText(OrderDonutActivity.this, "Please select a donut first.", Toast.LENGTH_SHORT).show();
            } else {
                showAddToOrderConfirmationDialog();
            }
        });
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        initializeDonuts();

        adapter = new DonutAdapter(this, donuts, new DonutAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                lastSelectedIndex = position;
                updateSubtotal();
                updateImageView(position);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    /**
     * Initializes donut items to be displayed in the RecyclerView.
     */
    private void initializeDonuts() {
        String[] itemNames = getResources().getStringArray(R.array.itemNames);
        donuts = new ArrayList<>();

        for (String itemName : itemNames) {
            String price = getPriceBasedOnFlavors(itemName); // You'll need to implement this method.
            donuts.add(new DonutItem(itemName, price));
        }
    }

    /**
     * Displays a confirmation dialog to the user to confirm the addition of the sandwich to their order
     */
    private void showAddToOrderConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm Order");
        builder.setMessage("Are you sure you want to add these donuts to your order?");

        builder.setPositiveButton("Yes", (dialog, id) -> {
            addDonutsToOrder();
            Toast.makeText(OrderDonutActivity.this, "Donuts added to order!", Toast.LENGTH_SHORT).show();
        });

        builder.setNegativeButton("No", (dialog, id) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Determines the price of donuts based on their flavors.
     *
     * @param flavor The flavor of the donut.
     * @return A string representing the price.
     */
    private String getPriceBasedOnFlavors(String flavor) {
        if (flavor.contains("Yeast")) {
            return "$1.79";
        } else if (flavor.contains("Cake")) {
            return "$1.89";
        } else if (flavor.contains("Donut Hole")) {
            return "$0.39";
        } else {
            return "$0.00";
        }
    }

    /**
     * Updates the subtotal displayed based on the selected donut and quantity.
     */
    private void updateSubtotal() {
        if (lastSelectedIndex == -1 || QuantityDonutSpinner.getSelectedItem() == null) return; // Ensure valid selection
        try {
            double price = getPriceBasedOnFlavor(donuts.get(lastSelectedIndex).getName());
            int quantity = Integer.parseInt(QuantityDonutSpinner.getSelectedItem().toString());
            double subtotal = price * quantity;
            SubtotalDonutTextView.setText(String.format("$%.2f", subtotal));
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Error calculating subtotal", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Retrieves the price of a donut based on its flavor.
     *
     * @param flavor The flavor of the donut.
     * @return The price as a double.
     */
    private double getPriceBasedOnFlavor(String flavor) {
        String priceStr = null;
        if (flavor.contains("Yeast")) {
            priceStr = "1.79";
        } else if (flavor.contains("Cake")) {
            priceStr = "1.89";
        } else if (flavor.contains("Donut Hole")) {
            priceStr = "0.39";
        }
        return Double.parseDouble(priceStr);
    }

    /**
     * Crude method to update the image view to reflect the selected donut type.
     *
     * @param position The position of the selected donut in the adapter.
     */
    private void updateImageView(int position) {
        if (position == 0) {
            DonutImageView.setImageResource(R.drawable.strawberryyeast);}
        else if (position == 1) {
            DonutImageView.setImageResource(R.drawable.glazedyeast);}
        else if (position == 2) {
            DonutImageView.setImageResource(R.drawable.caramelyeast);}
        else if (position == 3) {
            DonutImageView.setImageResource(R.drawable.cinnamonyeast);}
        else if (position == 4) {
            DonutImageView.setImageResource(R.drawable.coconutyeast);}
        else if (position == 5) {
            DonutImageView.setImageResource(R.drawable.mintyeast);}
        else if (position == 6) {
            DonutImageView.setImageResource(R.drawable.mochacake);}
        else if (position == 7) {
            DonutImageView.setImageResource(R.drawable.chaicake);}
        else if (position == 8) {
            DonutImageView.setImageResource(R.drawable.matchacake);}
        else if (position == 9) {
            DonutImageView.setImageResource(R.drawable.powdereddonuthole);}
        else if (position == 10) {
            DonutImageView.setImageResource(R.drawable.blueberrydonuthole);}
        else if (position == 11) {
            DonutImageView.setImageResource(R.drawable.lemondonuthole);}
    }

    /**
     * Adds the selected quantity of donuts to the current order.
     */
    private void addDonutsToOrder() {
        if (lastSelectedIndex == -1) {
            Toast.makeText(this, "Please select a donut first.", Toast.LENGTH_SHORT).show();
            return;
        }
        int quantity = Integer.parseInt(QuantityDonutSpinner.getSelectedItem().toString());
        DonutItem selectedDonut = donuts.get(lastSelectedIndex);
        String donutType = getDonutTypeAsString(selectedDonut.getName());
        String donutFlavor = getDonutFlavorAsString(selectedDonut.getName());
        Order currentOrder = Order.getInstance();
        for (int i = 0; i < quantity; i++) {
            Donut donut = new Donut(donutType, donutFlavor);
            currentOrder.addItem(donut);
        }
        Toast.makeText(this, quantity + " " + selectedDonut.getName() + " added to order!", Toast.LENGTH_SHORT).show();
    }

    /**
     * Converts the donut name into a string representing its type.
     * This method checks if the donut name contains specific keywords to determine the type.
     *
     * @param name includes the type and flavor.
     * @return A string that represents the donut type.
     */
    private String getDonutTypeAsString(String name) {
        if (name.contains("Yeast")) {
            return "YEAST";
        } else if (name.contains("Cake")) {
            return "CAKE";
        } else if (name.contains("Donut Hole")) {
            return "DONUT_HOLE";
        } else {
            return "UNKNOWN";
        }
    }

    /**
     * Converts the full name of the donut into a string suitable for processing
     *
     * @param name The full name of the donut.
     * @return A formatted version of the name
     */
    private String getDonutFlavorAsString(String name) {
        return name.toUpperCase().replace(" ", "_");
    }
}










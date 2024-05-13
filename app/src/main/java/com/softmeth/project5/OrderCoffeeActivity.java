package com.softmeth.project5;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/**
 * Activity for ordering coffee in the RU Café system.
 * It provides UI to select coffee cup size, quantity, and add-ons.
 * Calculates and displays the subtotal for the current selection and allows adding it to the order.
 *
 * @author Ridwan Sharkar
 */
public class OrderCoffeeActivity extends AppCompatActivity
{
    private CheckBox FrenchVanillaCheckBox;
    private CheckBox SweetCreamCheckBox;
    private CheckBox IrishCreamCheckBox;
    private CheckBox CaramelCheckBox;
    private CheckBox MochaCheckBox;
    private Spinner cupSizeSpinner;
    private Spinner quantitySpinner;
    private TextView subTotalTextView;
    private Button AddToOrderButton;
    /*============================================================================================*/

    /**
     * Initializes activity and its views when the activity is created.
     * Sets up UI elements for coffee order customization.
     *
     * @param savedInstanceState Contains data supplied if the activity is re-initialized after previously being shut down.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_order_coffee);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) ->
        {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initializeViews();
        setupSpinners();
        setupCheckBoxListeners();
        setupSpinnerListeners();
        setupAddToOrderButton();
    }

    /**
     * Initializes all view components used in the activity.
     */
    private void initializeViews()
    {
        cupSizeSpinner = findViewById(R.id.CupSizeSpinner);
        quantitySpinner = findViewById(R.id.QuantitySpinner);
        subTotalTextView = findViewById(R.id.SubtotalCoffeeTextView);
        AddToOrderButton = findViewById(R.id.AddToOrderCoffeeButton);
        FrenchVanillaCheckBox = findViewById(R.id.FrenchVanillaCheckBox);
        SweetCreamCheckBox = findViewById(R.id.SweetCreamCheckBox);
        IrishCreamCheckBox = findViewById(R.id.IrishCreamCheckBox);
        CaramelCheckBox = findViewById(R.id.CaramelCheckBox);
        MochaCheckBox = findViewById(R.id.MochaCheckBox);
    }

    /**
     * Sets up listeners for all checkbox components to update subtotal when their checked states change.
     */
    private void setupCheckBoxListeners()
    {
        CompoundButton.OnCheckedChangeListener listener = (compoundButton, isChecked) -> updateSubtotal(subTotalTextView);
        FrenchVanillaCheckBox.setOnCheckedChangeListener(listener);
        SweetCreamCheckBox.setOnCheckedChangeListener(listener);
        IrishCreamCheckBox.setOnCheckedChangeListener(listener);
        CaramelCheckBox.setOnCheckedChangeListener(listener);
        MochaCheckBox.setOnCheckedChangeListener(listener);
    }

    /**
     * Displays a confirmation dialog to the user to confirm the addition of the sandwich to their order
     */
    private void showAddToOrderConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm Order");
        builder.setMessage("Are you sure you want to add this coffee to your order?");

        builder.setPositiveButton("Yes", (dialog, which) -> {
            addCoffeeToOrder();
            Toast.makeText(OrderCoffeeActivity.this, "Coffee added to order!", Toast.LENGTH_SHORT).show();
        });

        builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Sets up spinners for selecting cup size and quantity.
     */
    private void setupSpinners()
    {
        Spinner cupSizeSpinner = findViewById(R.id.CupSizeSpinner);
        ArrayAdapter<CharSequence> cupSizeAdapter = ArrayAdapter.createFromResource(this,
                R.array.cup_sizes, android.R.layout.simple_spinner_item);
        cupSizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cupSizeSpinner.setAdapter(cupSizeAdapter);

        Spinner quantitySpinner = findViewById(R.id.QuantitySpinner);
        ArrayAdapter<CharSequence> quantityAdapter = ArrayAdapter.createFromResource(this,
                R.array.quantities, android.R.layout.simple_spinner_item);
        quantityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        quantitySpinner.setAdapter(quantityAdapter);
    }

    /**
     * Sets up listeners for spinner components to update subtotal when selection changes.
     */
    private void setupSpinnerListeners()
    {
        quantitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            { updateSubtotal(subTotalTextView); }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        cupSizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            { updateSubtotal(subTotalTextView); }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    /**
     * Updates the displayed subtotal for the coffee order based on current selections.
     *
     * @param subTotalTextView The TextView to display the subtotal.
     */
    private void updateSubtotal(TextView subTotalTextView)
    {
        double subtotal = calculateSubtotal();
        this.subTotalTextView.setText(String.format("$%.2f", subtotal));
    }

    /**
     * Calculates the subtotal based on selected coffee size, quantity, and add-ons.
     *
     * @return The calculated subtotal.
     */
    private double calculateSubtotal()
    {
        String selectedSize = cupSizeSpinner.getSelectedItem().toString();
        int selectedQuantity = Integer.parseInt(quantitySpinner.getSelectedItem().toString());
        Coffee coffeeOrder = new Coffee(selectedSize, countSelectedAddons());
        double priceForOne = coffeeOrder.price();
        return priceForOne * selectedQuantity;
    }

    /**
     * Counts the number of selected add-ons.
     *
     * @return The count of checked add-ons.
     */
    private int countSelectedAddons()
    {
        int count = 0;
        if (FrenchVanillaCheckBox.isChecked()) count++;
        if (SweetCreamCheckBox.isChecked()) count++;
        if (IrishCreamCheckBox.isChecked()) count++;
        if (CaramelCheckBox.isChecked()) count++;
        if (MochaCheckBox.isChecked()) count++;
        return count;
    }

    /**
     * Sets up the button to add the configured coffee to the order when clicked.
     * Integrates an AlertDialog for user confirmation.
     */
    private void setupAddToOrderButton() {
        AddToOrderButton.setOnClickListener(view -> {
            if (quantitySpinner.getSelectedItem() == null || cupSizeSpinner.getSelectedItem() == null) {
                Toast.makeText(this, "Please complete your selection before adding to order.", Toast.LENGTH_SHORT).show();
            } else {
                showAddToOrderConfirmationDialog();
            }
        });
    }

    /**
     * Adds the configured coffee to the current order and displays a confirmation message.
     */
    private void addCoffeeToOrder()
    {
        String selectedSize = cupSizeSpinner.getSelectedItem().toString();
        int selectedQuantity = Integer.parseInt(quantitySpinner.getSelectedItem().toString());
        int addonCount = countSelectedAddons();
        Coffee newCoffee = new Coffee(selectedSize, addonCount);

        Order currentOrder = Order.getInstance();
        for (int i = 0; i < selectedQuantity; i++) { currentOrder.addItem(newCoffee); }
        Toast.makeText(this, "Coffee added to order!", Toast.LENGTH_SHORT).show();
    }
}

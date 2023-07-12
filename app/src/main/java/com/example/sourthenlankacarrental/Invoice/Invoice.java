package com.example.sourthenlankacarrental.Invoice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.view.View;
import android.widget.Button;

import com.example.sourthenlankacarrental.R;

public class Invoice extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final String FILE_NAME = "invoice.pdf";

    private Button printButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);

        printButton = findViewById(R.id.buttonPrint);
        printButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                printInvoice();
            }
        });
    }

    private void printInvoice() {
        // Create a PrintManager instance
        PrintManager printManager = (PrintManager) getSystemService(Context.PRINT_SERVICE);

        // Set a print job name
        String jobName = getString(R.string.app_name) + " Document";

        // Get the content view to be printed
        View contentView = getWindow().getDecorView().findViewById(R.id.printlayout);

        // Create a print document adapter
        PrintDocumentAdapter printAdapter = new InvoicePrintAdapter(this, contentView);

        // Start the print job
        printManager.print(jobName, printAdapter, null);
    }
}
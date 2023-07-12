package com.example.sourthenlankacarrental.Invoice;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
import android.view.View;

import java.io.FileOutputStream;
import java.io.IOException;

@SuppressLint("NewApi")
public class InvoicePrintAdapter extends PrintDocumentAdapter {

    private Context context;
    private View contentView;

    public InvoicePrintAdapter(Context context, View contentView) {
        this.context = context;
        this.contentView = contentView;
    }

    @Override
    public void onLayout(PrintAttributes oldAttributes, PrintAttributes newAttributes, CancellationSignal cancellationSignal, LayoutResultCallback callback, Bundle extras) {
        if (cancellationSignal.isCanceled()) {
            callback.onLayoutCancelled();
            return;
        }

        PrintDocumentInfo builder = new PrintDocumentInfo.Builder("invoice.pdf")
                .setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
                .setPageCount(1)
                .build();

        callback.onLayoutFinished(builder, true);
    }

    @Override
    public void onWrite(PageRange[] pages, ParcelFileDescriptor destination, CancellationSignal cancellationSignal, WriteResultCallback callback) {
        if (cancellationSignal.isCanceled()) {
            callback.onWriteCancelled();
            return;
        }

        int margin =150; // desired margin size in pixels

        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(contentView.getWidth()+margin , contentView.getHeight()+50, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();
        canvas.translate(margin, 0); // translate the canvas to apply the margin

        // Draw only the desired part of the layout on the canvas
        contentView.draw(canvas);

        document.finishPage(page);

        try {
            document.writeTo(new FileOutputStream(destination.getFileDescriptor()));
            callback.onWriteFinished(new PageRange[]{PageRange.ALL_PAGES});
        } catch (IOException e) {
            callback.onWriteFailed(e.getMessage());
        } finally {
            document.close();
        }
    }
}




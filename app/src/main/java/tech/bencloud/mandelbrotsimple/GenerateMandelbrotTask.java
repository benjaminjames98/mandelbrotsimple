package tech.bencloud.mandelbrotsimple;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.Toast;

public class GenerateMandelbrotTask extends AsyncTask<Integer, Void, Bitmap> {

    double centreX;
    double centreY;
    double regionWidth;
    double regionHeight;
    double bitmapWidth;
    double bitmapHeight;
    ImageView mandelbrotIV;

    public GenerateMandelbrotTask(ImageView mandelbrotIV, double centreX, double centreY, double regionWidth, double regionHeight, double bitmapWidth, double bitmapHeight) {
        this.centreX = centreX;
        this.centreY = centreY;
        this.regionWidth = regionWidth;
        this.regionHeight = regionHeight;
        this.bitmapWidth = bitmapWidth;
        this.bitmapHeight = bitmapHeight;
        this.mandelbrotIV = mandelbrotIV;

        Toast.makeText(MainActivity.context, "AsyncTask started!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public Bitmap doInBackground(Integer... values) {
        return renderMandelbrot(centreX, centreY, regionWidth, regionHeight, bitmapWidth, bitmapHeight);
    }

    // Method to calculate and return a Bitmap of a Mandlebrot fractal at a given location, displaying a given region size,
// and at a given width and height
    private Bitmap renderMandelbrot(double centreX, double centreY, double regionWidth, double regionHeight, double bitmapWidth, double bitmapHeight) {
        // How many times to iterate over our mandelbrot calculation before aborting
        int maxIterations = 255;

        // Specify ARGB encoding, 8-bits per channel
        Bitmap.Config bitmapConf = Bitmap.Config.ARGB_8888;

        // Create a mutable bitmap (i.e. one we can modify!) of the specified size
        Bitmap bmp = Bitmap.createBitmap((int) bitmapWidth, (int) bitmapHeight, bitmapConf);

        // Loop through every horizontal row of pixels...
        for (int i = 0; i < bitmapWidth; i++) {
            // Loop through every vertical column of pixels
            for (int j = 0; j < bitmapHeight; j++) {
                double x0 = centreX - (regionWidth / 2) + regionWidth * (i / bitmapWidth);
                double y0 = centreY - (regionHeight / 2) + regionHeight * (j / bitmapHeight);

                // Initialise for this pixel's calculations
                double x = 0.0;
                double y = 0.0;
                int iteration = 0;

                // Perform the mandelbrot calculations
                while ((x * x + y * y < 4) && (iteration < maxIterations)) {
                    double xtemp = x * x - y * y + x0;
                    y = 2.0 * x * y + y0;
                    x = xtemp;
                    iteration++;
                }

                // Specify a colour (greyscale in this instance) based on the 'escape time' (i.e. iteration) of this pixel
                int colour = Color.argb(255, iteration, iteration, iteration);

                // Set that colour for the current pixel
                bmp.setPixel(i, j, colour);

            } // End of bitmapHeight loop

        } // End of bitmapWidth loop

        // Finished? Return the bitmap!
        return bmp;
    }

    @Override
    public void onPostExecute(Bitmap bitmap) {
        Toast.makeText(MainActivity.context, "Activity Finishing", Toast.LENGTH_LONG).show();
        this.mandelbrotIV.setImageBitmap(bitmap);
    }
}


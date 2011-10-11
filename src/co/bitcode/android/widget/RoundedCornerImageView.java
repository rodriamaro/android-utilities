/*
 * Android Utilities
 * Copyright (C) 2010-2011  Lorenzo Villani
 *
 * This library is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, version 3 of the License.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along
 * with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package co.bitcode.android.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * An {@link ImageView} with rounded corners.
 * 
 * @author Lorenzo Villani
 */
public class RoundedCornerImageView extends ImageView
{
        // -------------------------------------------------------------------------------------------------------------
        // Private Fields
        // -------------------------------------------------------------------------------------------------------------

        private final float density = getContext ().getResources ().getDisplayMetrics ().density;
        private float roundness;

        // -------------------------------------------------------------------------------------------------------------
        // Public Constructors
        // -------------------------------------------------------------------------------------------------------------

        /**
         * Simple constructor to use when creating a view from code.
         * 
         * @param context The Context the view is running in, through which it can access the current theme, resources,
         *                etc.
         */
        public RoundedCornerImageView ( Context context )
        {
                super ( context );

                init ();
        }

        /**
         * Constructor that is called when inflating a view from XML. This is called when a view is being constructed
         * from an XML file, supplying attributes that were specified in the XML file. This version uses a default style
         * of 0, so the only attribute values applied are those in the Context's Theme and the given AttributeSet.
         * <p>
         * The method onFinishInflate() will be called after all children have been added.
         * 
         * @param context The Context the view is running in, through which it can access the current theme, resources,
         *                etc.
         * @param attrs The attributes of the XML tag that is inflating the view.
         * @see #View(Context, AttributeSet, int)
         */
        public RoundedCornerImageView ( Context context, AttributeSet attrs )
        {
                super ( context, attrs );

                init ();
        }

        /**
         * Perform inflation from XML and apply a class-specific base style. This constructor of View allows subclasses
         * to use their own base style when they are inflating. For example, a Button class's constructor would call
         * this version of the super class constructor and supply <code>R.attr.buttonStyle</code> for
         * <var>defStyle</var>; this allows the theme's button style to modify all of the base view attributes (in
         * particular its background) as well as the Button class's attributes.
         * 
         * @param context The Context the view is running in, through which it can access the current theme, resources,
         *                etc.
         * @param attrs The attributes of the XML tag that is inflating the view.
         * @param defStyle The default style to apply to this view. If 0, no style will be applied (beyond what is
         *                included in the theme). This may either be an attribute resource, whose value will be
         *                retrieved from the current theme, or an explicit style resource.
         * @see #View(Context, AttributeSet)
         */
        public RoundedCornerImageView ( Context context, AttributeSet attrs, int defStyle )
        {
                super ( context, attrs, defStyle );

                init ();
        }

        // -------------------------------------------------------------------------------------------------------------
        // Public Methods
        // -------------------------------------------------------------------------------------------------------------

        /**
         * {@inheritDoc}
         */
        // TODO: Optimize
        @Override
        public void draw ( Canvas canvas )
        {
                final Bitmap composedBitmap;
                final Bitmap originalBitmap;
                final Canvas composedCanvas;
                final Canvas originalCanvas;
                final Paint paint;
                final int height;
                final int width;

                width = getWidth ();

                height = getHeight ();

                // Setup
                composedBitmap = Bitmap.createBitmap ( width, height, Bitmap.Config.ARGB_8888 );
                originalBitmap = Bitmap.createBitmap ( width, height, Bitmap.Config.ARGB_8888 );

                composedCanvas = new Canvas ( composedBitmap );
                originalCanvas = new Canvas ( originalBitmap );

                paint = new Paint ();
                paint.setAntiAlias ( true );
                paint.setColor ( Color.BLACK );

                // Draw original picture to a temporary bitmap
                super.draw ( originalCanvas );

                // Clear canvas and make it transparent
                composedCanvas.drawARGB ( 0, 0, 0, 0 );

                // Draw rounded rectangle
                composedCanvas.drawRoundRect ( new RectF ( 0, 0, width, height ), this.roundness, this.roundness, paint );

                // Compose the rounded corner mask with the original bitmap
                paint.setXfermode ( new PorterDuffXfermode ( Mode.SRC_IN ) );

                composedCanvas.drawBitmap ( originalBitmap, 0, 0, paint );

                // Draw composed canvas to view's canvas
                canvas.drawBitmap ( composedBitmap, 0, 0, new Paint () );
        }

        // -------------------------------------------------------------------------------------------------------------
        // Getters and Setters
        // -------------------------------------------------------------------------------------------------------------

        public float getRoundness ()
        {
                return this.roundness / this.density;
        }

        public void setRoundness ( float roundness )
        {
                this.roundness = roundness * this.density;
        }

        // -------------------------------------------------------------------------------------------------------------
        // Private Methods
        // -------------------------------------------------------------------------------------------------------------

        private void init ()
        {
                setRoundness ( 5 );
        }
}

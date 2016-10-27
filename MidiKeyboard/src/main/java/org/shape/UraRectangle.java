/*
 * Copyright 2013-2016 the Uranoplums Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 *
 * $Id: UraRectangle.java$
 */
package org.shape;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;


/**
 * UraRectangleクラス。<br>
 *
 * @since 2016/10/05
 * @author syany
 */
public class UraRectangle extends Rectangle {

    /**
     *
     */
    protected UraRectangle() {}

    /**
     * @param width
     * @param height
     */
    protected UraRectangle(double width, double height) {
        super(width, height);
    }

    /**
     * @param width
     * @param height
     * @param paint
     */
    protected UraRectangle(double width, double height, Paint paint) {
        super(width, height, paint);
    }

    /**
     * @param x
     * @param y
     * @param width
     * @param height
     */
    protected UraRectangle(double x, double y, double width, double height) {
        super(x, y, width, height);
    }

    protected UraRectangle(UraRectangle.Builder builder) {
        if (builder.x != 0) this.setX(builder.x);
        if (builder.y != 0) this.setY(builder.y);
        if (builder.w != 0) this.setWidth(builder.w);
        if (builder.h != 0) this.setHeight(builder.h);

        if (builder.paint != null) this.setFill(builder.paint);
    }
    /**
     * Builderクラス。<br>
     *
     * @since 2016/10/06
     * @author syany
     */
    public static class Builder {
        private double x;
        private double y;
        private double w;
        private double h;
        private Paint paint;
        public Builder() {
        }

        /**
         * @param x x  を設定します
         */
        public UraRectangle.Builder x(double x) {
            this.x = x;
            return this;
        }

        /**
         * @param y y  を設定します
         */
        public UraRectangle.Builder y(double y) {
            this.y = y;
            return this;
        }

        /**
         * @param w w  を設定します
         */
        public UraRectangle.Builder w(double w) {
            this.w = w;
            return this;
        }

        /**
         * @param h h  を設定します
         */
        public UraRectangle.Builder h(double h) {
            this.h = h;
            return this;
        }

        /**
         * @param paint paint  を設定します
         */
        public UraRectangle.Builder paint(Paint paint) {
            this.paint = paint;
            return this;
        }
        /**
         * UraRectangleのビルド
         * @return
         */
        public UraRectangle build() {
            return new UraRectangle(this);
        }
    }
}

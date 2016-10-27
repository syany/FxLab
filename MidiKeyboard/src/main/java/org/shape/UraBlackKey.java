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
 * $Id: UraBlackKey.java$
 */
package org.shape;

import javafx.scene.Node;
import javafx.scene.paint.Paint;

import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;

import org.sound.NoteProgram;


/**
 * UraBlackKeyクラス。<br>
 *
 * @since 2016/10/13
 * @author syany
 */
public class UraBlackKey extends UraKeyboard {

    static final String CSS_KEY_BASE = "keyBase";
    static final String CSS_B_KEY_OFF = "blackKey";
    static final String CSS_B_KEY_ON = "blackKeyOn";
    /**
     * @param builder
     */
    public UraBlackKey(Builder builder) {
        super(builder);
        this.getStyleClass().add(UraBlackKey.CSS_KEY_BASE);
        this.getStyleClass().add(UraBlackKey.CSS_B_KEY_OFF);
    }

    public static class BlackKeyBuilder extends UraKeyboard.KeyBuiler implements UraOnTouchPressedListener, UraOnTouchReleasedListener {

        public BlackKeyBuilder(Receiver receiver, ShortMessage shortMessage, NoteProgram noteProgram) {
            super(receiver, shortMessage, noteProgram);
        }

        /* (非 Javadoc)
         * @see org.shape.UraKeyboard.KeyBuiler#setNote(int)
         */
        @Override
        public BlackKeyBuilder setNote(int note) {
            return BlackKeyBuilder.class.cast(super.setNote(note));
        }

        /* (非 Javadoc)
         * @see org.shape.UraKeyboard.KeyBuiler#setVelocity(int)
         */
        @Override
        public BlackKeyBuilder setVelocity(int velocity) {
            return BlackKeyBuilder.class.cast(super.setVelocity(velocity));
        }

        /* (非 Javadoc)
         * @see org.shape.UraKeyboard.KeyBuiler#setReceiver(javax.sound.midi.Receiver)
         */
        @Override
        public BlackKeyBuilder setReceiver(Receiver receiver) {
            return BlackKeyBuilder.class.cast(super.setReceiver(receiver));
        }

        /* (非 Javadoc)
         * @see org.shape.UraKeyboard.KeyBuiler#setShortMessage(javax.sound.midi.ShortMessage)
         */
        @Override
        public BlackKeyBuilder setShortMessage(ShortMessage shortMessage) {
            return BlackKeyBuilder.class.cast(super.setShortMessage(shortMessage));
        }

        /* (非 Javadoc)
         * @see org.shape.UraKeyboard.KeyBuiler#x(double)
         */
        @Override
        public BlackKeyBuilder x(double x) {
            return BlackKeyBuilder.class.cast(super.x(x));
        }

        /* (非 Javadoc)
         * @see org.shape.UraKeyboard.KeyBuiler#y(double)
         */
        @Override
        public BlackKeyBuilder y(double y) {
            return BlackKeyBuilder.class.cast(super.y(y));
        }

        /* (非 Javadoc)
         * @see org.shape.UraKeyboard.KeyBuiler#w(double)
         */
        @Override
        public BlackKeyBuilder w(double w) {
            return BlackKeyBuilder.class.cast(super.w(w));
        }

        /* (非 Javadoc)
         * @see org.shape.UraKeyboard.KeyBuiler#h(double)
         */
        @Override
        public BlackKeyBuilder h(double h) {
            return BlackKeyBuilder.class.cast(super.h(h));
        }

        /* (非 Javadoc)
         * @see org.shape.UraKeyboard.KeyBuiler#paint(javafx.scene.paint.Paint)
         */
        @Override
        public BlackKeyBuilder paint(Paint paint) {
            return BlackKeyBuilder.class.cast(super.paint(paint));
        }

        /* (非 Javadoc)
         * @see org.shape.UraKeyboard.KeyBuiler#build()
         */
        @Override
        public UraBlackKey build() {
            return new UraBlackKey(this);
        }

        public void onTouchReleasedListen(Node node) {
            node.getStyleClass().clear();
            node.getStyleClass().add(UraBlackKey.CSS_KEY_BASE);
            node.getStyleClass().add(UraBlackKey.CSS_B_KEY_OFF);

        }

        public void onTouchPressedListen(Node node) {
            node.getStyleClass().clear();
            node.getStyleClass().add(UraBlackKey.CSS_KEY_BASE);
            node.getStyleClass().add(UraBlackKey.CSS_B_KEY_ON);
        }

    }
}

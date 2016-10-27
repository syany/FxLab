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
 * $Id: UraWhiteKey.java$
 */
package org.shape;

import javafx.scene.Node;
import javafx.scene.paint.Paint;

import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;

import org.sound.NoteProgram;


/**
 * UraWhiteKeyクラス。<br>
 *
 * @since 2016/10/13
 * @author syany
 */
public class UraWhiteKey extends UraKeyboard {

    static final String CSS_KEY_BASE = "keyBase";
    static final String CSS_W_KEY_OFF = "whiteKey";
    static final String CSS_W_KEY_ON = "whiteKeyOn";
    /**
     * @param builder
     */
    public UraWhiteKey(Builder builder) {
        super(builder);
        this.getStyleClass().add(UraWhiteKey.CSS_KEY_BASE);
        this.getStyleClass().add(UraWhiteKey.CSS_W_KEY_OFF);
    }

    public static class WhiteKeyBuiler extends UraKeyboard.KeyBuiler implements UraOnTouchPressedListener, UraOnTouchReleasedListener {

        public WhiteKeyBuiler(Receiver receiver, ShortMessage shortMessage, NoteProgram noteProgram) {
            super(receiver, shortMessage, noteProgram);
        }

        /* (非 Javadoc)
         * @see org.shape.UraKeyboard.KeyBuiler#setNote(int)
         */
        @Override
        public WhiteKeyBuiler setNote(int note) {
            return WhiteKeyBuiler.class.cast(super.setNote(note));
        }

        /* (非 Javadoc)
         * @see org.shape.UraKeyboard.KeyBuiler#setVelocity(int)
         */
        @Override
        public WhiteKeyBuiler setVelocity(int velocity) {
            return WhiteKeyBuiler.class.cast(super.setVelocity(velocity));
        }

        /* (非 Javadoc)
         * @see org.shape.UraKeyboard.KeyBuiler#setReceiver(javax.sound.midi.Receiver)
         */
        @Override
        public WhiteKeyBuiler setReceiver(Receiver receiver) {
            return WhiteKeyBuiler.class.cast(super.setReceiver(receiver));
        }

        /* (非 Javadoc)
         * @see org.shape.UraKeyboard.KeyBuiler#setShortMessage(javax.sound.midi.ShortMessage)
         */
        @Override
        public WhiteKeyBuiler setShortMessage(ShortMessage shortMessage) {
            return WhiteKeyBuiler.class.cast(super.setShortMessage(shortMessage));
        }

        /* (非 Javadoc)
         * @see org.shape.UraKeyboard.KeyBuiler#x(double)
         */
        @Override
        public WhiteKeyBuiler x(double x) {
            return WhiteKeyBuiler.class.cast(super.x(x));
        }

        /* (非 Javadoc)
         * @see org.shape.UraKeyboard.KeyBuiler#y(double)
         */
        @Override
        public WhiteKeyBuiler y(double y) {
            return WhiteKeyBuiler.class.cast(super.y(y));
        }

        /* (非 Javadoc)
         * @see org.shape.UraKeyboard.KeyBuiler#w(double)
         */
        @Override
        public WhiteKeyBuiler w(double w) {
            return WhiteKeyBuiler.class.cast(super.w(w));
        }

        /* (非 Javadoc)
         * @see org.shape.UraKeyboard.KeyBuiler#h(double)
         */
        @Override
        public WhiteKeyBuiler h(double h) {
            return WhiteKeyBuiler.class.cast(super.h(h));
        }

        /* (非 Javadoc)
         * @see org.shape.UraKeyboard.KeyBuiler#paint(javafx.scene.paint.Paint)
         */
        @Override
        public WhiteKeyBuiler paint(Paint paint) {
            return WhiteKeyBuiler.class.cast(super.paint(paint));
        }

        /* (非 Javadoc)
         * @see org.shape.UraKeyboard.KeyBuiler#build()
         */
        @Override
        public UraWhiteKey build() {
            return new UraWhiteKey(this);
        }

        public void onTouchReleasedListen(Node node) {
            node.getStyleClass().clear();
            node.getStyleClass().add(UraWhiteKey.CSS_KEY_BASE);
            node.getStyleClass().add(UraWhiteKey.CSS_W_KEY_OFF);

        }

        public void onTouchPressedListen(Node node) {
            node.getStyleClass().clear();
            node.getStyleClass().add(UraWhiteKey.CSS_KEY_BASE);
            node.getStyleClass().add(UraWhiteKey.CSS_W_KEY_ON);
        }

    }
}

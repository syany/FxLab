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
 * $Id: UraSoundReceive.java$
 */
package org.shape;

import javafx.scene.paint.Paint;

import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;


/**
 * UraSoundReceiveクラス。<br>
 *
 * @since 2016/10/11
 * @author syany
 */
public class UraSoundReceive extends UraRectangle {
    /** MIDIレシーバ */
    private final Receiver receiver;
    /** レシーバへ送信するメッセージインスタンス */
    private final ShortMessage shortMessage;


    /**
     * @return receiver を返却します
     */
    public final Receiver getReceiver() {
        return receiver;
    }


    /**
     * @return shortMessage を返却します
     */
    public final ShortMessage getShortMessage() {
        return shortMessage;
    }

    /**
     * @param builder
     */
    protected UraSoundReceive(UraRectangle.Builder builder) {
        super(builder);
        UraSoundReceive.SRBuilder source = UraSoundReceive.SRBuilder.class.cast(builder);
        this.receiver = source.receiver;
        this.shortMessage = source.shortMessage;
    }

    public static class SRBuilder extends UraRectangle.Builder {
        private Receiver receiver;
        private ShortMessage shortMessage;
        /**
         * @param receiver MIDIレシーバ
         * @param shortMessage レシーバへ送信するメッセージインスタンス
         */
        public SRBuilder(Receiver receiver, ShortMessage shortMessage) {
            super();
            this.receiver = receiver;
            this.shortMessage = shortMessage;
        }

        /**
         * @param receiver receiver  を設定します
         */
        public UraSoundReceive.SRBuilder setReceiver(Receiver receiver) {
            this.receiver = receiver;
            return this;
        }


        /**
         * @param shortMessage shortMessage  を設定します
         */
        public UraSoundReceive.SRBuilder setShortMessage(ShortMessage shortMessage) {
            this.shortMessage = shortMessage;
            return this;
        }

        /* (非 Javadoc)
         * @see org.shape.UraRectangle.Builder#x(double)
         */
        @Override
        public UraSoundReceive.SRBuilder x(double x) {
            return UraSoundReceive.SRBuilder.class.cast(super.x(x));
        }

        /* (非 Javadoc)
         * @see org.shape.UraRectangle.Builder#y(double)
         */
        @Override
        public UraSoundReceive.SRBuilder y(double y) {
            return UraSoundReceive.SRBuilder.class.cast(super.y(y));
        }

        /* (非 Javadoc)
         * @see org.shape.UraRectangle.Builder#w(double)
         */
        @Override
        public UraSoundReceive.SRBuilder w(double w) {
            return UraSoundReceive.SRBuilder.class.cast(super.w(w));
        }

        /* (非 Javadoc)
         * @see org.shape.UraRectangle.Builder#h(double)
         */
        @Override
        public UraSoundReceive.SRBuilder h(double h) {
            return UraSoundReceive.SRBuilder.class.cast(super.h(h));
        }

        /* (非 Javadoc)
         * @see org.shape.UraRectangle.Builder#paint(javafx.scene.paint.Paint)
         */
        @Override
        public UraSoundReceive.SRBuilder paint(Paint paint) {
            return UraSoundReceive.SRBuilder.class.cast(super.paint(paint));
        }

        /* (非 Javadoc)
         * @see org.shape.UraRectangle.Builder#build()
         */
        @Override
        public UraSoundReceive build() {
            return new UraSoundReceive(this);
        }

    }
}

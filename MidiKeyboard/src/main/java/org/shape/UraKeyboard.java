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
 * $Id: UraKeyboard.java$
 */
package org.shape;

import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.TouchEvent;
import javafx.scene.input.TouchPoint;
import javafx.scene.paint.Paint;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;

import org.sound.NoteProgram;



/**
 * Keyboardクラス。<br>
 *
 * @since 2016/10/05
 * @author syany
 */
public class UraKeyboard extends UraSoundReceive {
    /** チャネル、楽器情報 */
    private NoteProgram noteProgram;
    /** 音階(C:60) */
    private int note = 60;
    /** 音量 */
    private int velocity= 100;

    private Parent parentNode;

    private boolean noteOn = false;

    /**
     * @param noteProgram noteProgram  を設定します
     */
    public final UraKeyboard setNoteProgram(NoteProgram noteProgram) {
        this.noteProgram = noteProgram;
        return this;
    }


    /**
     * @param note note  を設定します
     */
    public final UraKeyboard setNote(int note) {
        this.note = note;
        return this;
    }


    /**
     * @param velocity velocity  を設定します
     */
    public final UraKeyboard setVelocity(int velocity) {
        this.velocity = velocity;
        return this;
    }



    /**
     * @return parentNode を返却します
     */
    public final Parent getParentNode() {
        return parentNode;
    }



    /**
     * @param parentNode parentNode  を設定します
     */
    public final void setParentNode(Parent parentNode) {
        this.parentNode = parentNode;
    }



    /**
     * @return noteProgram を返却します
     */
    public final NoteProgram getNoteProgram() {
        return noteProgram;
    }



    /**
     * @return note を返却します
     */
    public final int getNote() {
        return note;
    }



    /**
     * @return velocity を返却します
     */
    public final int getVelocity() {
        return velocity;
    }


    /**
     * @param builder
     */
    protected UraKeyboard(UraRectangle.Builder builder) {
        super(builder);
        final UraKeyboard.KeyBuiler source = UraKeyboard.KeyBuiler.class.cast(builder);
        this.noteProgram = source.noteProgram;
        this.note = source.note;
        this.velocity = source.velocity;
        final UraKeyboard self = this;
        this.setOnTouchPressed(new EventHandler<TouchEvent>(){
            public void handle(TouchEvent touchEvent) {
                try {
                    EventTarget target =  touchEvent.getTarget();
                    UraKeyboard targetNode = UraKeyboard.class.cast(target);
                    final TouchPoint touchPoint = touchEvent.getTouchPoint();
                    boolean s1 = targetNode.getParent().isPressed();
                    boolean hover = isHover(touchPoint.getX(), touchPoint.getY());
                    boolean noteon = self.isNoteOn();
//                    System.out.println("[PPP] SELF Key setOnTouchPressed n["+self.getNote()+"]p["+self.isPressed()+
//                            "("+s1+")]f["+self.isFocused()
//                            +"]h["+hover+"]noteon["+noteon+"]");
                    if (self.isPressed()) {
//                        System.out.println(">>> [T.Pressed] Already Pressed");
//                        touchEvent.consume();
                        return;
                    }
                    if (self.isNoteOn()) {
//                        System.out.println(">>> [T.Pressed] Already Note on");
//                        touchEvent.consume();
                          return;
                      }

//                    System.out.println("tc f:" + self.isFocused());
//                    System.out.println("touch pressed !! [" + note + "](fc:"+ self.isFocused()+")");
                    source.pressedListen.onTouchPressedListen(self);
                    setNoteOn();
                    self.setPressed(true);
//                    touchEvent.consume();
//                    System.out.println("<<<>>> [T.Pressed] Note on");

//                    self.getShortMessage().setMessage(ShortMessage.NOTE_ON, noteProgram.getChanel(), note, velocity);
//                    self.getReceiver().send(self.getShortMessage(), 0);
//                    touchEvent.consume();
//                } catch (InvalidMidiDataException e) {
//                    e.printStackTrace();
                } finally {
//                    self.setPressed(true);
                    touchEvent.consume();
                }
            }
        });
        this.setOnTouchReleased(new EventHandler<TouchEvent>(){
            public void handle(TouchEvent touchEvent) {
                try {
                    EventTarget target =  touchEvent.getTarget();
                    UraKeyboard targetNode = UraKeyboard.class.cast(target);
                    final TouchPoint _touchPoint = touchEvent.getTouchPoint();
                    boolean s1 = targetNode.getParent().isPressed();
                    boolean hover = isHover(_touchPoint.getX(), _touchPoint.getY());
                    boolean noteon = self.isNoteOn();
//                    System.out.println("[RRR] SELF Key setOnTouchReleased n["+self.getNote()+"]p["+self.isPressed()+
//                            "("+s1+")]f["+self.isFocused()
//                            +"]h["+hover+"]noteon["+noteon+"]");

                    if (!self.isNoteOn()) {
//                        System.out.println(">>> Already Note off");
//                        touchEvent.consume();
                        return;
                    }

                    boolean stationaryFlag = false;
                    for (final TouchPoint touchPoint : touchEvent.getTouchPoints()) {
                        final TouchPoint.State state = touchPoint.getState();
                        if (TouchPoint.State.STATIONARY.equals(state)) {
                            if (isHover(touchPoint.getX(), touchPoint.getY())) {
//                                System.out.println("State["+state+"], X["+touchPoint.getX()+"], Y["+touchPoint.getY()+"], (this is "+self.getX()+" >= x >= "+(self.getX() + self.getWidth())+" , "+self.getY()+" >= y >= "+(self.getY() + self.getHeight())+")");
                                stationaryFlag = true;
                            }
//                        } else if (State.RELEASED.equals(state)) {
//                            if (isHover(touchPoint.getX(), touchPoint.getY())) {
//                                System.out.println(">> State["+state+"], X["+touchPoint.getX()+"], Y["+touchPoint.getY()+"], (this is "+self.getX()+" >= x >= "+(self.getX() + self.getWidth())+" , "+self.getY()+" >= y >= "+(self.getY() + self.getHeight())+")");
//                            }
                        }
                    }
                    if (stationaryFlag) {
//                        System.out.println(">>> not releae yet");
//                        touchEvent.consume();
                        return;
                    }

                    source.releasedListen.onTouchReleasedListen(self);
                    setNoteOff();
                    self.setPressed(false);
//                    touchEvent.consume();
//                    System.out.println("[RRR End] SELF Key setOnTouchReleased n["+self.getNote()+"]p["+self.isPressed()+
//                            "("+s1+")]f["+self.isFocused()
//                            +"]h["+hover+"]noteon["+noteon+"]");
                } finally {
//                    self.setPressed(false);
                    touchEvent.consume();
                }
            }
        });

//        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            public void handle(MouseEvent mouseEvent) {
//                EventTarget eventTarget = mouseEvent.getTarget();
//              UraKeyboard targetNode = UraKeyboard.class.cast(eventTarget);
//
////                if (!self.isPressed()) {
////                    System.out.println(">>> [M.Clicked] Already not Pressed");
////                    mouseEvent.consume();
////                    return;
////                }
//                if (!self.isNoteOn()) {
//                    System.out.println(">>> [M.Clicked] Already Note off ["+targetNode.note+"]");
//                    mouseEvent.consume();
//                    return;
//                }
//                source.releasedListen.onTouchReleasedListen(self);
//                setNoteOff();
//                mouseEvent.consume();
//                self.setPressed(false);
//            }
//        });
//        this.setOnMousePressed(new EventHandler<MouseEvent>() {
//            public void handle(MouseEvent mouseEvent) {
////                if (self.isPressed()) {
////                    System.out.println(">>> [M.Pressed] Already Pressed");
////                    mouseEvent.consume();
////                    return;
////                }
//                if (self.isNoteOn()) {
//                    System.out.println(">>> [M.Pressed] Already Note on");
//                    mouseEvent.consume();
//                    return;
//                }
//                source.pressedListen.onTouchPressedListen(self);
//                setNoteOn();
//                mouseEvent.consume();
//                self.setPressed(true);
//            }
//        });

//        this.setOnMouseExited(new EventHandler<MouseEvent>(){
//            public void handle(MouseEvent mouseEvent) {
//                try {
//                    EventTarget target =  mouseEvent.getTarget();
//                    UraKeyboard targetNode = UraKeyboard.class.cast(target);
//                    boolean s1 = targetNode.getParent().isPressed();
//                    boolean hover = isHover(mouseEvent.getX(), mouseEvent.getY());
//                    boolean noteon = self.isNoteOn();
//                    System.out.println("M-Exited n["+note+"]p["+self.isPressed()+"("+s1+")]f["+self.isFocused()
//                            +"]h["+hover+"]noteon["+noteon+"]");
//                    if (self.isNoteOn()) {
//                        if (!isHover(mouseEvent.getX(), mouseEvent.getY())) {
//                            source.releasedListen.onTouchReleasedListen(self);
//                            setNoteOff();
//                            mouseEvent.consume();
//                            self.setPressed(false);
//                            System.out.println("M-Exited not Hoverd n["+note+"]p["+self.isPressed()+"("+s1+")]f["+self.isFocused()
//                                +"]h["+hover+"]noteon["+noteon+"]");
//                        } else if (self.isPressed()) {
//                            source.releasedListen.onTouchReleasedListen(self);
//                            setNoteOff();
//                            mouseEvent.consume();
//                            self.setPressed(false);
//                            System.out.println("M-Exited Pressed n["+note+"]p["+self.isPressed()+"("+s1+")]f["+self.isFocused()
//                                    +"]h["+hover+"]noteon["+noteon+"]");
//                        }
//                    } else if (isHover(mouseEvent.getX(), mouseEvent.getY())) {
//                        source.releasedListen.onTouchReleasedListen(self);
//                        setNoteOff();
//                        mouseEvent.consume();
//                        self.setPressed(false);
//                        System.out.println("M-Exited Just Hoverd n["+note+"]p["+self.isPressed()+"("+s1+")]f["+self.isFocused()
//                            +"]h["+hover+"]noteon["+noteon+"]");
//                    }
//                } finally {
////                    mouseEvent.consume();
////                    self.setPressed(false);
////                    mouseEvent.consume();
//                }
//            }
//        });
    }

    public boolean isHover(final double x, final double y) {
        return this.getX() <= x && (this.getX() + this.getWidth()) > x &&
                this.getY() <= y && (this.getY() + this.getHeight()) > y;
    }


    /**
     * @return noteOn を返却します
     */
    public final boolean isNoteOn() {
        return noteOn;
    }

    /**
     */
    public final void setNoteOn() {
        try {
            this.getShortMessage().setMessage(ShortMessage.NOTE_ON, noteProgram.getChanel(), note, velocity);
            this.getReceiver().send(this.getShortMessage(), 0);
            this.noteOn = true;
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }
    }


    /**
     * @param noteOn noteOn  を設定します
     */
    public final void setNoteOff() {
        try {
            this.getShortMessage().setMessage(ShortMessage.NOTE_OFF, noteProgram.getChanel(), note, velocity);
            this.getReceiver().send(this.getShortMessage(), 0);
            this.noteOn = false;
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }
    }

    public static class KeyBuiler extends UraSoundReceive.SRBuilder implements UraOnTouchPressedListener, UraOnTouchReleasedListener {
        private NoteProgram noteProgram;
        private int note = 60;
        private int velocity= 100;

        private UraOnTouchPressedListener pressedListen = this;
        private UraOnTouchReleasedListener releasedListen = this;

        /**
         * @param receiver MIDIレシーバ
         * @param shortMessage レシーバへ送信するメッセージインスタンス
         * @param noteProgram チャネル、楽器情報
         */
        public KeyBuiler(Receiver receiver, ShortMessage shortMessage, NoteProgram noteProgram) {
            super(receiver, shortMessage);
            this.noteProgram = noteProgram;
        }

        /**
         * @param note note  を設定します
         */
        public UraKeyboard.KeyBuiler setNote(int note) {
            this.note = note;
            return this;
        }

        /**
         * @param velocity velocity  を設定します
         */
        public UraKeyboard.KeyBuiler setVelocity(int velocity) {
            this.velocity = velocity;
            return this;
        }


        /**
         * @param pressedListen pressedListen  を設定します
         */
        public final UraKeyboard.KeyBuiler setPressedListen(UraOnTouchPressedListener pressedListen) {
            this.pressedListen = pressedListen;
            return this;
        }


        /**
         * @param releasedListen releasedListen  を設定します
         */
        public final UraKeyboard.KeyBuiler setReleasedListen(UraOnTouchReleasedListener releasedListen) {
            this.releasedListen = releasedListen;
            return this;
        }

        /* (非 Javadoc)
         * @see org.shape.UraSoundReceive.SRBuilder#setReceiver(javax.sound.midi.Receiver)
         */
        @Override
        public UraKeyboard.KeyBuiler setReceiver(Receiver receiver) {
            return UraKeyboard.KeyBuiler.class.cast(super.setReceiver(receiver));
        }

        /* (非 Javadoc)
         * @see org.shape.UraSoundReceive.SRBuilder#setShortMessage(javax.sound.midi.ShortMessage)
         */
        @Override
        public UraKeyboard.KeyBuiler setShortMessage(ShortMessage shortMessage) {
            return UraKeyboard.KeyBuiler.class.cast(super.setShortMessage(shortMessage));
        }

        /* (非 Javadoc)
         * @see org.shape.UraSoundReceive.SRBuilder#x(double)
         */
        @Override
        public UraKeyboard.KeyBuiler x(double x) {
            return UraKeyboard.KeyBuiler.class.cast(super.x(x));
        }

        /* (非 Javadoc)
         * @see org.shape.UraSoundReceive.SRBuilder#y(double)
         */
        @Override
        public UraKeyboard.KeyBuiler y(double y) {
            return UraKeyboard.KeyBuiler.class.cast(super.y(y));
        }

        /* (非 Javadoc)
         * @see org.shape.UraSoundReceive.SRBuilder#w(double)
         */
        @Override
        public UraKeyboard.KeyBuiler w(double w) {
            return UraKeyboard.KeyBuiler.class.cast(super.w(w));
        }

        /* (非 Javadoc)
         * @see org.shape.UraSoundReceive.SRBuilder#h(double)
         */
        @Override
        public UraKeyboard.KeyBuiler h(double h) {
            return UraKeyboard.KeyBuiler.class.cast(super.h(h));
        }

        /* (非 Javadoc)
         * @see org.shape.UraSoundReceive.SRBuilder#paint(javafx.scene.paint.Paint)
         */
        @Override
        public UraKeyboard.KeyBuiler paint(Paint paint) {
            return UraKeyboard.KeyBuiler.class.cast(super.paint(paint));
        }

        /* (非 Javadoc)
         * @see org.shape.UraRectangle.Builder#build()
         */
        @Override
        public UraKeyboard build() {
            return new UraKeyboard(this);
        }

        public void onTouchReleasedListen(Node node) {}

        public void onTouchPressedListen(Node node) {}
    }
}

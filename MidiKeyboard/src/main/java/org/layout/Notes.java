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
 * $Id: Notes.java$
 */
package org.layout;

import static org.uranoplums.typical.collection.factory.UraListFactory.*;

import java.awt.AWTException;
import java.awt.Robot;
import java.net.URL;
import java.util.List;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.event.EventType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.TouchEvent;
import javafx.scene.input.TouchPoint;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;

import org.shape.UraBlackKey;
import org.shape.UraKeyboard;
import org.shape.UraWhiteKey;
import org.sound.NoteProgram;
import org.util.KeyBoardUtils;

/**
 * Notesクラス。<br>
 *
 * @since 2016/10/12
 * @author syany
 */
public class Notes extends Application {
    static final int WHITE_KEY_WIDTH = 80;
    static final int BLACK_KEY_WIDTH = 50;
    static final int BLACK_KEY_OFFSET = (WHITE_KEY_WIDTH - BLACK_KEY_WIDTH) / 2;
    static final int WHITE_KEY_HEIGHT = 350;
    static final int BLACK_KEY_HEIGHT = 200;

    private Robot robot;
    @Override
    public void start(Stage stage) throws Exception {
        /* ステージ初期 */
        stage.setTitle("キーボード");

        /* レイアウト初期 */
        URL url = getClass().getResource("Notes.xml");
        URL cssUrl = getClass().getResource("Notes.css");
        AnchorPane root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        stage.setScene(scene);
//        setFilter(stage); // stageにイベントフィルタを仕掛ける
        scene.getStylesheets().add(cssUrl.toExternalForm());

        /* アイテム初期＋イベント */
        this.items(root);

        stage.show();
    }

    private void setFilter(Stage stage) {
        stage.addEventFilter(EventType.ROOT, e -> System.out.println(e));
    }
    protected void items(final AnchorPane pane) {
        try {

            try {
                robot = new Robot();
            } catch (AWTException e) {
                throw new IllegalArgumentException( e );
            }
            List<UraKeyboard> keyList = newArrayList();
            Receiver receiver = MidiSystem.getReceiver();
            ShortMessage sMessage = new ShortMessage();
            NoteProgram noteProgram = new NoteProgram(0, 80);
//            NoteProgram noteProgram2 = new NoteProgram(1, 20);

            sMessage.setMessage(ShortMessage.PROGRAM_CHANGE, noteProgram.getChanel(), noteProgram.getProgram(), 0);
            receiver.send(sMessage, 0);

//            sMessage.setMessage(ShortMessage.PROGRAM_CHANGE, noteProgram2.getChanel(), noteProgram2.getProgram(), 0);
//            receiver.send(sMessage, 0);

            final List<UraWhiteKey> whiteKeyList = newArrayList();
            List<UraBlackKey> blackKeyList = newArrayList();

            for (int note = 53, wIdx = 0; note < 80; note++) {
                if (KeyBoardUtils.isBlack(note)) {
                    final int w = (wIdx - 1) * WHITE_KEY_WIDTH;
                    UraBlackKey bKey = new UraBlackKey.BlackKeyBuilder(receiver, sMessage, noteProgram)
                    .x((w + WHITE_KEY_WIDTH / 2) + BLACK_KEY_OFFSET).y(0).w(BLACK_KEY_WIDTH).h(BLACK_KEY_HEIGHT).setNote(note).build();
                    blackKeyList.add(bKey);

                    keyList.add(bKey);
                } else {
                    final UraWhiteKey wKey = new UraWhiteKey.WhiteKeyBuiler(receiver, sMessage, noteProgram)
                        .x(wIdx * WHITE_KEY_WIDTH).y(0).w(WHITE_KEY_WIDTH).h(WHITE_KEY_HEIGHT).setNote(note).build();
                    whiteKeyList.add(wKey);
                    keyList.add(wKey);
                    wIdx++;
                }
            }


            ObservableList<Node> nodes = pane.getChildren();
            for (final UraWhiteKey wKey : whiteKeyList) {
                wKey.setParentNode(pane);
                nodes.add(wKey);
//                keyList.add(wKey);
            }

            for (final UraBlackKey bKey : blackKeyList) {
                bKey.setParentNode(pane);
                nodes.add(bKey);
//                keyList.add(bKey);
            }

            pane.setOnTouchMoved(new EventHandler<TouchEvent>() {
                public void handle(TouchEvent touchEvent) {
                    try {

    //                  robot.mouseMove((int)touchEvent.getTouchPoint().getX(), (int)touchEvent.getTouchPoint().getY());
    //                    boolean isPressedFierd = false;
                        boolean isBlackKeyHover = false;

                        for (final UraKeyboard keyNote: blackKeyList) {
                            EventTarget target =  touchEvent.getTarget();
                            UraKeyboard targetNode = UraKeyboard.class.cast(target);
                            final TouchPoint _touchPoint = touchEvent.getTouchPoint();
                            boolean s1 = targetNode.getParent().isPressed();
                            boolean hover = keyNote.isHover(_touchPoint.getX(), _touchPoint.getY());
                            boolean noteon = keyNote.isNoteOn();
    //                        System.out.println("[PANE] SELF Key setOnTouchMoved n["+keyNote.getNote()+
    //                                "]p["+keyNote.isPressed()+"("+s1+")]f["+keyNote.isFocused()
    //                                +"]h["+hover+"]noteon["+noteon+"]");
                            if (!isBlackKeyHover) {
                                isBlackKeyHover = hover;
                            }

                            if (!noteon) {
                                if (hover) {
                                    TouchEvent pEvent = new TouchEvent(keyNote, touchEvent.getTarget(), TouchEvent.TOUCH_PRESSED,
                                            touchEvent.getTouchPoint(), touchEvent.getTouchPoints(), touchEvent.getEventSetId(),
                                            touchEvent.isShiftDown(), touchEvent.isControlDown(),
                                            touchEvent.isAltDown(), touchEvent.isMetaDown());

                                    keyNote.fireEvent(pEvent);
                                    pEvent.consume();
                                }
                            } else {
                                if (!hover) {
                                    TouchEvent rEvent = new TouchEvent(keyNote, touchEvent.getTarget(), TouchEvent.TOUCH_RELEASED,
                                            touchEvent.getTouchPoint(), touchEvent.getTouchPoints(), touchEvent.getEventSetId(),
                                            touchEvent.isShiftDown(), touchEvent.isControlDown(),
                                            touchEvent.isAltDown(), touchEvent.isMetaDown());
                                    keyNote.fireEvent(rEvent);
                                    rEvent.consume();
//                                    touchEvent.consume();
                                }
                            }
                        }

                        for (final UraKeyboard keyNote: whiteKeyList) {
                            EventTarget target =  touchEvent.getTarget();
                            UraKeyboard targetNode = UraKeyboard.class.cast(target);
                            final TouchPoint _touchPoint = touchEvent.getTouchPoint();
                            boolean s1 = targetNode.getParent().isPressed();
                            boolean hover = keyNote.isHover(_touchPoint.getX(), _touchPoint.getY());
                            boolean noteon = keyNote.isNoteOn();

                            if (!noteon) {
                                if (hover && !isBlackKeyHover) {
                                    TouchEvent pEvent = new TouchEvent(keyNote, touchEvent.getTarget(), TouchEvent.TOUCH_PRESSED,
                                            touchEvent.getTouchPoint(), touchEvent.getTouchPoints(), touchEvent.getEventSetId(),
                                            touchEvent.isShiftDown(), touchEvent.isControlDown(),
                                            touchEvent.isAltDown(), touchEvent.isMetaDown());

                                    keyNote.fireEvent(pEvent);
                                    pEvent.consume();
//                                    touchEvent.consume();
                                }
                            } else {
                                if (!hover || (hover && isBlackKeyHover)) {
                                    TouchEvent rEvent = new TouchEvent(keyNote, touchEvent.getTarget(), TouchEvent.TOUCH_RELEASED,
                                            touchEvent.getTouchPoint(), touchEvent.getTouchPoints(), touchEvent.getEventSetId(),
                                            touchEvent.isShiftDown(), touchEvent.isControlDown(),
                                            touchEvent.isAltDown(), touchEvent.isMetaDown());
                                    keyNote.fireEvent(rEvent);
                                    rEvent.consume();
//                                    touchEvent.consume();
                                }
                            }
                        }

                    } finally {
                        touchEvent.consume();
                    }
                }
            });
//            pane.setOnTouchReleased(new EventHandler<TouchEvent>() {
//                @Override
//                public void handle(TouchEvent touchEvent) {
//                    for (final UraKeyboard keyNote: keyList) {
//                        EventTarget target =  touchEvent.getTarget();
//                        UraKeyboard targetNode = UraKeyboard.class.cast(target);
//                        final TouchPoint _touchPoint = touchEvent.getTouchPoint();
//                        boolean s1 = targetNode.getParent().isPressed();
//                        boolean hover = keyNote.isHover(_touchPoint.getX(), _touchPoint.getY());
//                        boolean noteon = keyNote.isNoteOn();
//                        System.out.println("[PANE] SELF Key OnTouchReleased n["+keyNote.getNote()+
//                                "]p["+keyNote.isPressed()+"("+s1+")]f["+keyNote.isFocused()
//                                +"]h["+hover+"]noteon["+noteon+"]");
//
//                        if (!hover) {
//                            System.out.println("[PANE End] SELF Key OnTouchReleased XXX Not Hover XXXn["+keyNote.getNote()+
//                                    "]p["+keyNote.isPressed()+"("+s1+")]f["+keyNote.isFocused()
//                                    +"]h["+hover+"]noteon["+noteon+"]");
//                            continue;
//                        }
//                        boolean stationaryFlag = false;
//                        for (final TouchPoint touchPoint : touchEvent.getTouchPoints()) {
//                            final TouchPoint.State state = touchPoint.getState();
//                            if (TouchPoint.State.STATIONARY.equals(state)) {
//                                if (keyNote.isHover(touchPoint.getX(), touchPoint.getY())) {
//                                    System.out.println("[PANE] SELF Key OnTouchReleased >> State["+state+
//                                            "], X["+touchPoint.getX()+"], Y["+touchPoint.getY()+"], (this is "+
//                                            keyNote.getX()+" >= x >= "+(keyNote.getX() + keyNote.getWidth())+" , "+
//                                            keyNote.getY()+" >= y >= "+(keyNote.getY() + keyNote.getHeight())+")");
//                                    stationaryFlag = true;
//                                }
////                            } else if (State.RELEASED.equals(state)) {
////                                if (isHover(touchPoint.getX(), touchPoint.getY())) {
////                                    System.out.println(">> State["+state+"], X["+touchPoint.getX()+"], Y["+touchPoint.getY()+"], (this is "+self.getX()+" >= x >= "+(self.getX() + self.getWidth())+" , "+self.getY()+" >= y >= "+(self.getY() + self.getHeight())+")");
////                                }
//                            }
//                        }
//                        if (stationaryFlag) {
//                            System.out.println(">>> [PANE End] SELF Key OnTouchReleased not releae yet");
//                            touchEvent.consume();
//                            return;
//                        }
//
//                        keyNote.fireEvent(touchEvent);
//                        touchEvent.consume();
//
//                        System.out.println("[PANE End] SELF Key OnTouchReleased n["+keyNote.getNote()+
//                                "]p["+keyNote.isPressed()+"("+s1+")]f["+keyNote.isFocused()
//                                +"]h["+hover+"]noteon["+noteon+"]");
//                        return;
//                    }
//                }
//            });
            pane.setOnScrollFinished(new EventHandler<ScrollEvent>() {
                @Override
                public void handle(ScrollEvent scrollEvent) {
                    if (!scrollEvent.isInertia()) {
                        for (final UraKeyboard keyNote: keyList) {
                            EventTarget target =  scrollEvent.getTarget();
                            UraKeyboard targetNode = UraKeyboard.class.cast(target);
    //                        final TouchPoint _touchPoint = scrollEvent;
                            boolean s1 = targetNode.getParent().isPressed();
                            boolean hover = keyNote.isHover(scrollEvent.getX(), scrollEvent.getY());
                            boolean noteon = keyNote.isNoteOn();
//                            System.out.println("[PANE] SELF Key OnScrollFinished n["+keyNote.getNote()+
//                                    "]p["+keyNote.isPressed()+"("+s1+")]f["+keyNote.isFocused()
//                                    +"]h["+hover+"]noteon["+noteon+"]");
                            if (!keyNote.isPressed() && !hover && !noteon) {
                                continue;
                            }
                            if ((!keyNote.isPressed() || !hover || !noteon) ||
                                    (keyNote.isPressed() && hover && noteon)) {
                                final TouchPoint touchPoint = new TouchPoint(
                                        0, TouchPoint.State.RELEASED,
                                        scrollEvent.getX(), scrollEvent.getY(),
                                        scrollEvent.getScreenX(), scrollEvent.getScreenY(),
                                        target, scrollEvent.getPickResult());
                                List<TouchPoint> touchPointList = newArrayList(1);
                                touchPointList.add(touchPoint);
                                TouchEvent rEvent = new TouchEvent(
                                        keyNote, target, TouchEvent.TOUCH_RELEASED,
                                        touchPoint, touchPointList, 0,
                                        scrollEvent.isShiftDown(), scrollEvent.isControlDown(),
                                        scrollEvent.isAltDown(), scrollEvent.isMetaDown());
                                keyNote.fireEvent(rEvent);
                                rEvent.consume();

//                              MouseEvent mEvent = new MouseEvent(keyNote, target, MouseEvent.MOUSE_CLICKED,
//                                      scrollEvent.getX(), scrollEvent.getY(), scrollEvent.getScreenX(), scrollEvent.getScreenY(), null,
//                                      scrollEvent.getTouchCount(), scrollEvent.isShiftDown(), scrollEvent.isControlDown(),
//                                      scrollEvent.isAltDown(), scrollEvent.isMetaDown(), false, false, false, false, false, false, null);
//                              keyNote.fireEvent(mEvent);
    //                          touchEvent.consume();
                            }
                        }
                    }
                }
            });
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        Application.launch(Notes.class, args);
    }
}

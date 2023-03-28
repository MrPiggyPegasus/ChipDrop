/*
 Copyright (c) 2023. "MrPiggyPegasus"
 This file is part of the ChipDrop Connect4 engine, see https://github.com/MrPiggyPegasus/ChipDrop.

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
 */

/*
 Copyright (c) 2023. "MrPiggyPegasus"
 This file is part of the ChipDrop Connect4 engine, see https://github.com/MrPiggyPegasus/ChipDrop.

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
 */

package gui;

import connect4.Board;
import connect4.Engine;

public class BestMoveSubject {
    public int bestMove;
    public GamePanel observer;
    Thread findMoveThread;
    Board pos;
    public void findMove() {
        pos.resetMinimax();
        findMoveThread = new Thread(() -> {
            try {
                bestMove = pos.bestMove();
                broadcast();
            } catch (Engine.ProcessTerminatedException ignored) {}
        });
        findMoveThread.start();
        findMoveThread.interrupt();
    }

    public BestMoveSubject(GamePanel sub, Board pos) {
        this.pos = pos;
        observer = sub;
    }
    public void cancel() {
        pos.killMinimax();
    }

    public void broadcast() {
        observer.updateBestMove(this);
    }
}

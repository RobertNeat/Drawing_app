package com.example.drawing_app;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class DrawingSurface extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private static final int FRAME_TIME = (int) (1000.0 / 60.0); // 60 FPS
    private final SurfaceHolder holder;             //zmienna do kontrolowania i monitorowania powierzchni
    private final Object lock = new Object();       //tworzenie sekcji krytycznych
    private final Path path = new Path();
    private final Paint path_paint = new Paint();    //przechowuje argumenty linii rysującej
    private final Paint dot_paint = new Paint();     //przechowuje argumenty zakończeń linii rysujących
    private int paint_color = Color.WHITE;           //początkowy kolo farby
    private Thread thread;                          //wątek odświerzający kanwę
    private boolean running;                        //kontrolowanie pracy wątku (flaga pozwala na zapis na kanwę kiedy palec dotyka ekran)
    private Bitmap bitmap;                          //utworzenie bitmapy na którą będziemy przenosili rysowane linie
    private Canvas canvas;                          //utworzenie kanwy, bitmapę przypiszemy do niej w funkcji

    public DrawingSurface(Context context, AttributeSet attrs) {
        super(context, attrs);
        holder = getHolder();                       //pobranie pojemnika powierzchni, który będzie modyfikował i kontrolował powierzchnię
        holder.addCallback(this);                   //ustawienie sluchacza interfejsu

        path_paint.setAntiAlias(true);               //ustawienie anti-aliasingu, żeby krawędzie nie były poszarpane
        path_paint.setColor(paint_color);             //ustawienie wybranego koloru
        path_paint.setStyle(Paint.Style.STROKE);
        path_paint.setStrokeJoin(Paint.Join.ROUND);
        path_paint.setStrokeWidth(12);               //ustawienie grubości linii

        dot_paint.setAntiAlias(true);
        dot_paint.setColor(paint_color);
        dot_paint.setStyle(Paint.Style.FILL);
    }


    //metoda czyszcząca kanwę (zamalowuje całą kanwę na czarno)
    public void clear() {
        synchronized (lock) {
            if (canvas != null) {
                canvas.drawColor(Color.BLACK);
                path.reset();
            }
        }
    }


    //metoda zmieniająca kolor farby dla następnej linii (dla zmiany koloru przyciskiem)
    public void setpaint_color(int paint_color) {
        this.paint_color = paint_color;
        path_paint.setColor(paint_color);
        dot_paint.setColor(paint_color);
        path.reset();
    }


    //metoda dostarczająca komponentowi informacje związane z dotknięciem ekranu (obsługa dotknięcia ekranu)
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        performClick();                                             //trzeba było wywołać, żeby nie wyrzucało błędów
        float x = event.getX();
        float y = event.getY();
        int DOT_RADIUS = 18;

        switch (event.getAction()) {                                //obsługa MotionEvent i pobranie informacji o kursorze
            case MotionEvent.ACTION_DOWN: {
                synchronized (lock) {
                    canvas.drawCircle(x, y, DOT_RADIUS, dot_paint);  //narysowanie kropki w miejscu dotknięcia
                }
                path.moveTo(x, y);                                  //przesunięcie ścieżki pod pozycję dotknięcia
                return true;
            }
            case MotionEvent.ACTION_MOVE: {
                path.lineTo(x, y);                                  //narysowanie linii do pozycji dotknięcia
                synchronized (lock) {                               //synchronizacja zmian w rysunku (modyfikacja rysunku przez wątek)
                    canvas.drawPath(path, path_paint);               //naniesienie ścieżki na kanwę
                }
                return true;
            }
            case MotionEvent.ACTION_UP: {
                synchronized (lock) {
                    canvas.drawCircle(x, y, DOT_RADIUS, dot_paint);  //narysowanie kropki w miejscu puszczenia
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }


    //utworzenie powierzchni rysowania
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888); //stworzenie bitmapy o wymiarach ekranowych
        canvas = new Canvas(bitmap);                                                    //przypisanie kanwy do bitmapy
        startDrawingThread();
    }


    //wywoływane przy aktualicazji wielkości powierzchni rysowania
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }


    //zniszczenie powierzchni rysowania (trzeba zabezlieczyć przed rysowaniem przed wykonaniem tej metody)
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        stopDrawingThread();
    }


    //uruchomienie wątku rysującego
    public void startDrawingThread() {
        running = true;
        thread = new Thread(this, "DrawSurface");
        thread.start();
    }


    //zatrzymanie wątku rysującego (w momencie puszzcenia ekranu)
    public void stopDrawingThread() {
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    //metoda rysująca uruchamiana w osobnym wątku
    @Override
    public void run() {
        while (running) {
            Canvas localCanvas = null;                              //pobranie kanwy i zablokowanie
            try {
                synchronized (holder) {                             //zabezpieczenie przed równoległym odczytem i modyfikacją
                    if (!holder.getSurface().isValid()) continue;
                    localCanvas = holder.lockCanvas(null);
                    synchronized (lock) {
                        if (running) {
                            localCanvas.drawBitmap(bitmap, 0, 0, null);
                        }
                    }
                }
            } catch (Exception e) {
                Log.w("blad", "Exception while locking/unlocking");
            } finally {
                if (localCanvas != null) {
                    holder.unlockCanvasAndPost(localCanvas);        //odblokowanie kanwy i wyświetlenie narysowanej linii
                }
            }
            try {
                Thread.sleep(FRAME_TIME);
            } catch (InterruptedException ignored) {
            }
        }
    }
}
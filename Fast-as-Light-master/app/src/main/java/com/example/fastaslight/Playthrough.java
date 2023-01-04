package com.example.fastaslight;

import static com.example.fastaslight.StartPlaythrough.navigationBarHeight;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Playthrough extends SurfaceView implements Runnable {

    private Thread thread;
    private boolean isPlaying, isGameOver = false, screenSwitched = false;
    private PlaythroughBackground background1, background2;
    private int screenX, screenY;
    private Paint paint;
    private Player player;
    private List<Bullet> bullets;
    private List<MedKit> med_kits;
    private Cloud[] clouds;
    private Random random;
    private List<Currency> currency;
    private List<EnemyAI> enemyAIs;
    private List<Plane> planes;
    private List<Plane> planeTrash;
    private int spawn_rate;
    private Resources res;
    public static StartPlaythrough playthrough;
    public List<Bullet> bulletTrash;
    public List<Currency> currencyTrash;
    public List<EnemyAI> enemyTrash;
    public static int totalCurrency, continues, timeCounter, cardHeight, shotCost;
    public static float screenRatioX, screenRatioY, speedMultiplier;

    public Playthrough(StartPlaythrough playthrough, int screenX, int screenY) {
        super(playthrough);

        totalCurrency = 0;
        continues = 0;
        timeCounter = 0;
        speedMultiplier = 1;

        this.playthrough = playthrough;

        this.screenX = screenX;
        this.screenY = screenY;

        random = new Random();

        //sets ratio for different screen sizes
        screenRatioX = 1440 / screenX;
        screenRatioY = 3120 / screenY;

        //sets the background
        int start_environment = random.nextInt(5);
        if(start_environment % 2 == 1)
        {
            if(start_environment == 5)
            {
                start_environment = 0;
            }
            else
            {
                start_environment += 1;
            }
        }

        background1 = new PlaythroughBackground(start_environment, screenX, screenY, getResources());
        background2 = new PlaythroughBackground(start_environment + 1, screenX, screenY, getResources());

        //sets position of second background
        background1.y = 0;
        background2.y = -(2 * screenY);

        player = new Player(this, screenX, screenY, getResources());

        bullets = new ArrayList<>();

        bulletTrash = new ArrayList<>();

        currency = new ArrayList<>();

        currencyTrash = new ArrayList<>();

        med_kits = new ArrayList<>();

        enemyAIs = new ArrayList<>();

        enemyTrash = new ArrayList<>();

        planes = new ArrayList<>();

        planeTrash = new ArrayList<>();

        paint = new Paint();

        clouds = new Cloud[7];

        for (int i = 0; i < 7; i++) {
            Cloud cloud = new Cloud(getResources(), screenY);
            clouds[i] = cloud;
        }
    }

    //GameLoop
    @Override
    public void run() {
        while (isPlaying) {
            update();
            playthrough.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    cardHeight = playthrough.gameUiCard.getHeight();
                    //Updates UI time
                    playthrough.updateTime(Math.floorDiv(timeCounter, 18));
                    //Updates the UI current currency
                    playthrough.updateCurrencyText();

                    playthrough.updateBulletCostText();
                    //Updates UI for players health
                    switch (player.health) {
                        case 3:
                            playthrough.updateHealth(R.drawable.full_health);
                            break;
                        case 2:
                            playthrough.updateHealth(R.drawable.health_damage1);
                            break;
                        case 1:
                            playthrough.updateHealth(R.drawable.health_damage2);
                            break;
                        case 0:
                            playthrough.updateHealth(R.drawable.empty_health);
                            break;
                    }
                }
            });
            draw();
            sleep();
        }
    }

    private void update() {

        timeCounter++;
        shotCost = 3 + ((int) (timeCounter / (18*60)) * 3);

        if (timeCounter % (18*20) == 0) {
            speedMultiplier += .5;
        }

        //moves the background down
        background1.y += 10 * screenRatioY * speedMultiplier;
        background2.y += 10 * screenRatioY * speedMultiplier;


        //checks if player is pressing shoot button
        if (player.shooting) {
            player.toShoot++;
        }

        //loop background once out of screen
        if (background1.y > screenY) {
            background1.y = background2.y -(2 * screenY);
            if (!screenSwitched) {
                background1.switchBackground();
                background1.switchBackground();
                screenSwitched = true;
            }
        }
        if (background2.y > screenY) {
            background2.y = background1.y - (2 * screenY);
            if (screenSwitched) {
                background2.switchBackground();
                background2.switchBackground();
                screenSwitched = false;
            }

        }

        //checks if a directional button is pressed and moves character towards that direction
        if (player.moveLeft) {
            player.x -= 30 * screenRatioX;
        }
        if (player.moveRight) {
            player.x += 30 * screenRatioX;
        }
        if (player.moveUp) {
            player.y -= 30 * screenRatioY;
        }
        if (player.moveDown) {
            player.y += 30 * screenRatioY;
        }

        //checks if player is moving off screen and moves them on screen
        if (player.x < 0) {
            player.x = 0;
        }
        if (player.x > screenX - player.width) {
            player.x = screenX - player.width;
        }
        if (player.y < cardHeight) {
            player.y = cardHeight;
        }
        if (player.y > screenY - player.height - (2 * navigationBarHeight)) {
            player.y = screenY - player.height - (2 * navigationBarHeight);
        }

        for (Bullet bullet: bullets) {
            //if bullet is off screen then add to trash list
            if (bullet.y < 0 || bullet.y > screenY) {
                bulletTrash.add(bullet);
            }

            //if bullet has been shot by the player, then move bullet down
            else if (bullet.shot && bullet.shotByPlayer)
            {
                bullet.y -= 50 * screenRatioX;
            }
            //if bullet has been shot by the enemy, then move bullet down
            else
            {
                bullet.y += 50 * screenRatioX;
            }

            //marks a bullet as shot
            if(!bullet.shot && bullet.shotByPlayer && player.shooting && totalCurrency > 2)
            {
                bullet.shot = true;
                totalCurrency -= shotCost;
            }

            //checks if player touches enemy bullet
            if (Rect.intersects(bullet.getCollisionShape(), player.getCollisionShape()) && !bullet.shotByPlayer) {
                bullet.y = screenY + 1;
                player.health--;

                if (player.health < 1)
                {
                    isGameOver = true;
                }
            }

            for(EnemyAI enemy: enemyAIs)
            {
                //checks if enemy touches a bullet
                if (Rect.intersects(bullet.getCollisionShape(), enemy.getCollisionShape()))
                {
                    bullet.y = screenY + 1;
                    enemy.y = screenY;
                }
            }

        }

        for (Bullet bullet: bullets) {
            //if bullet was added to List and was not shot then remove bullet from List
            if (!bullet.shot) {
                bullets.remove(bullet);
            }
        }

        //remove extra bullets
        bulletTrash.clear();

        for (Cloud cloud :clouds) {
            //move clouds down based on cloud speed
            cloud.y += (cloud.speed * speedMultiplier);

            //if cloud moves off screen give cloud a new speed
            if (cloud.y > screenY) {
                int bound = (int) (30 * screenRatioY);
                cloud.speed = random.nextInt(bound);

                //checks if cloud speed is less than minimum speed
                if (cloud.speed < 10 * screenRatioY) {
                    //sets cloud speed to minimum
                    cloud.speed = (int) (10 * screenRatioX);
                }

                //set cloud position to above screen at a random x position
                cloud.y = -cloud.height;
                cloud.x = random.nextInt(screenX - cloud.width);
            }
        }

        // Spawn a coin with a random value every four seconds
        if (timeCounter % (18*4) == 0) {
            currency.add(new Currency(getResources(), screenX));
        }

        for (Currency coin: currency) {
            //move coin down based on coin speed
            coin.y += coin.speed;

            //checks if player touches coin
            if (Rect.intersects(coin.getCollisionShape(), player.getCollisionShape())) {
                //adds coin value to the current currency and moves coin offscreen
                totalCurrency += coin.value;
                coin.y = screenY;
            }

            //if coin is offscreen add coin to "trash"
            if (coin.y > screenY) {
                currencyTrash.add(coin);
            }
        }

        // Spawns a enemy type every five seconds
        if (timeCounter % (18*5) == 0)
        {
            spawn_rate = random.nextInt(100);

            //  20% chance for an enemy to be a grappler enemy type
            if (spawn_rate > 80)
            {
                enemyAIs.add(new GrapplerEnemy(screenX, getResources(), player));
            }
            //  30% chance for an enemy to be a ranged enemy type
            else if (spawn_rate > 50)
            {
                enemyAIs.add(new RangedEnemy(screenX, getResources(), this));
            }
            //  50% for an enemy to be a basic enemy type
            else
            {
                enemyAIs.add(new BasicEnemy(screenX, getResources()));
            }
        }

        for (EnemyAI enemy : enemyAIs)
        {
            enemy.update();

            if (Rect.intersects(enemy.getCollisionShape(), player.getCollisionShape()))
            {
                //if enemy touches player decrease player health
                player.health--;
                //move enemy position off screen
                enemy.y = screenY;
                //if player health is less than 1 then Game Over
                if (player.health < 1)
                {
                    isGameOver = true;
                }
                return;
            }

            if (enemy.y > screenY) {
                enemyTrash.add(enemy);
            }
        }

        for (EnemyAI enemy : enemyTrash) {
            enemyAIs.remove(enemy);
        }

        enemyTrash.clear();

        if (random.nextInt(100) > 98 && player.health < 3)
        {
            med_kits.add(new MedKit(getResources(), screenY));
        }

        for (MedKit med_kit: med_kits)
        {
            med_kit.y += med_kit.speed;

            if (Rect.intersects(med_kit.getCollisionShape(), player.getCollisionShape()))
            {
                if (player.health < 3) {
                    player.health++;
                }
                med_kit.y = screenY;
            }
        }

        //remove coins in trash from the currency list
        for (Currency coin: currencyTrash) {
            currency.remove(coin);
        }

        currencyTrash.clear();

        if (timeCounter % (18*7) == 0)
        {
            spawn_rate = random.nextInt(100);
            System.out.println(spawn_rate);
            if (spawn_rate > 50) {
                planes.add(new Plane(getResources(), screenX, screenY, true));
            } else {
                planes.add(new Plane(getResources(), screenX, screenY, false));
            }
        }

        for (Plane plane: planes) {
            plane.updatePlane();

            if (Rect.intersects(plane.getCollisionShape(), player.getCollisionShape()))
            {
                //if plane touches player decrease player health
                player.health--;
                //move plane position off screen
                if (!plane.flipped) {
                    plane.x = -plane.width;
                } else {
                    plane.x = screenX;
                }
                //if player health is less than 1 then Game Over
                if (player.health < 1)
                {
                    isGameOver = true;
                }
                return;
            }

            if ((plane.x < -plane.width && !plane.flipped) || (plane.x > screenX && plane.flipped)) {
                planeTrash.add(plane);
            }
        }

        for (Plane plane : planeTrash) {
            planes.remove(plane);
        }

        planeTrash.clear();
    }

    //draws all bitmaps onto the screen
    private void draw() {
        if (getHolder().getSurface().isValid()) {
            Canvas canvas = getHolder().lockCanvas();
            //draws the backgrounds onto screen
            canvas.drawBitmap(background1.background, background1.x, background1.y, paint);
            canvas.drawBitmap(background2.background, background2.x, background2.y, paint);

            //for every bullet in List draw the bullet
            for (Bullet bullet: bullets) {
                canvas.drawBitmap(bullet.bullet, bullet.x, bullet.y, paint);
            }

            //for every coin in List draw the coin
            for (Currency coin: currency) {
                canvas.drawBitmap(coin.getCoin(), coin.x, coin.y, paint);
            }

            for (MedKit med_kit: med_kits)
            {
                canvas.drawBitmap(med_kit.getMedKit(), med_kit.x, med_kit.y, paint);
            }

            for (EnemyAI enemy: enemyAIs)
            {
                canvas.drawBitmap(enemy.getEnemy(), enemy.x, enemy.y, paint);
            }

            for (Plane plane : planes) {
                canvas.drawBitmap(plane.getPlane(), plane.x, plane.y, paint);
            }

            //if Game Over draw the dead player image
            if (isGameOver) {
                isPlaying = false;
                canvas.drawBitmap(player.getDead(), player.x, player.y, paint);
                getHolder().unlockCanvasAndPost(canvas);
                waitBeforeExiting();
                return;
            }

            canvas.drawBitmap(player.getPlayer(), player.x, player.y, paint);

            //for every cloud in List draw the cloud
            for (Cloud cloud: clouds) {
                canvas.drawBitmap(cloud.getCloud(), cloud.x, cloud.y, paint);
            }

            getHolder().unlockCanvasAndPost(canvas);
        }
    }

    //sleep to update frame every 60 seconds
    private void sleep() {
        try {
            Thread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume() {
        isPlaying = true;
        thread = new Thread(this);
        thread.start();

    }

    public void pause() {
        try {
            isPlaying = false;
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    //add new bullet to List at players position
    public void newBullet(int x, int y, int shooter_width, Boolean shotByPlayer)
    {
        //Bullet bullet = new Bullet(x, y, shooter_width, shotByPlayer, getResources());
        Bullet bullet = new Bullet(x, y, shooter_width, shotByPlayer, getResources());
        bullets.add(bullet);
    }

    //get the player object
    public Player getPlayer() {
        return player;
    }

    //clears screen and increments amount player has continued after character dies
    private void waitBeforeExiting() {
        try {
            player = new Player(this, screenX, screenY, getResources());
            for (Cloud cloud: clouds) {
                cloud.y = -cloud.height;
            }
            for (Bullet bullet: bullets) {
                bullet.y = -bullet.height;
            }
            for (Currency coin: currency) {
                coin.y = -coin.width;
            }
            for (EnemyAI enemy : enemyAIs) {
                enemy.y = screenY;
            }
            isGameOver = false;
            continues++;
            Thread.sleep(2000);
            playthrough.startActivity(new Intent(playthrough, Continue.class));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

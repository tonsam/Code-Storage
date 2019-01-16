public class UFO
{
    public UFO(int maxX, int maxY)
    {
        hit = false;
        wantToAttack = true;
        attackPos = false;
        attacking = false;
        phase = false;
        sound = false;
        bomber = false;
        this.maxX = maxX;
        this.maxY = maxY;
        respawn();
    }

    public void respawn()
    {
        if(bomber)
            hasBomber = false;
        
        count = 0;
        hit = false;
        wantToAttack = false;
        attacking = false;
        attackPos = false;
        phase = false;
        sound = false;
        if(hasBomber)
            bomber = false;
        else
            bomber = (int)(Math.random() * (double)100) < 25;
        if(bomber)
        {
            hasBomber = true;
            x = -100;
            y = 20;
            destX = maxX + 100;
            destY = 20;
            shield = 5;
        } else
        {
            x = (int)(Math.random() * (double)maxX);
            y = -40 - (int)(Math.random() * (double)200);
            destX = (int)(Math.random() * (double)maxX);
            destY = (int)((Math.random() * (double)maxY) / (double)2);
            shield = 0;
        }
    }

    public void step(int d)
    {
        if(destX > x)
        {
            int x_ = x + d;
            if(x_ > destX)
                x_ = destX;
            x = x_;
        } else
        {
            int x_ = x - d;
            if(x_ < destX)
                x_ = destX;
            x = x_;
        }
        if(destY > y)
        {
            int y_ = y + d;
            if(y_ > destY)
                y_ = destY;
            y = y_;
        } else
        {
            int y_ = y - d;
            if(y_ < destY)
                y_ = destY;
            y = y_;
        }
        if(x == destX && y == destY)
            changeDestination();
        count++;
        if(count > 50)
            wantToAttack = true;
    }

    public void changeDestination()
    {
        if(bomber)
            respawn();
        else
        if(wantToAttack)
        {
            if(attackPos)
            {
                attacking = true;
            } else
            {
                destX = (int)(Math.random() * (double)maxX) - 30;
                destY = (maxY / 2 + (int)(Math.random() * (double)50)) - 25;
                attackPos = true;
                sound = true;
            }
        } else
        {
            destX = (int)(Math.random() * (double)maxX);
            destY = (int)(Math.random() * (double)maxY) / 2;
        }
    }

    int x;
    int y;
    int destX;
    int destY;
    int maxX;
    int maxY;
    int count;
    int shield;
    boolean hit;
    private boolean wantToAttack;
    private boolean attackPos;
    boolean attacking;
    boolean phase;
    boolean sound;
    boolean bomber;
    private static boolean hasBomber = false;
}
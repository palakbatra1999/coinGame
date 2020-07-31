package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;


import java.util.ArrayList;
import java.util.Random;

class MarioGame extends ApplicationAdapter {
	SpriteBatch batch;
Texture background;
Texture[] man;
int manstate=0;
int pause=0;
	float gravity=0.2f;
	float velocity=0;
	int manY=0;
    int score=0;
Rectangle manRectangle;
Texture dizzy;
	ArrayList<Integer> coinXs=new ArrayList<>();
    ArrayList<Integer> coinYs=new ArrayList<>();
    ArrayList<Integer> coinx=new ArrayList<>();
    ArrayList<Integer> coiny=new ArrayList<>();
    ArrayList<Rectangle> coinrectangles=new ArrayList<>();
    ArrayList<Rectangle> bombrectangles=new ArrayList<>();
    Texture coin;
    int coinCount=0;
	Random random;
	Texture bomb;
	int bombCount=0;
BitmapFont bitmapFont;
int gamestate=0;
	@Override
	public void create () {
		batch = new SpriteBatch();
        background=new Texture("bg.png");
        man=new Texture[4];
        man[0]=new Texture("frame1.png");
        man[1]=new Texture("frame2.png");
        man[2]=new Texture("frame3.png");
        man[3]=new Texture("frame4.png");
        manY=Gdx.graphics.getHeight()/2;
        coin=new Texture("coin.png");
        random=new Random();
        bomb=new Texture("bomb.png");
        bitmapFont=new BitmapFont();
        bitmapFont.setColor(Color.WHITE);
        bitmapFont.getData().setScale(10);
        dizzy=new Texture("dizzy1.png");
     //   manRectangle=new Rectangle();
	}
	public  void makeCoin()
    {
        float height=random.nextFloat()*Gdx.graphics.getHeight();
        coinYs.add((int)height);
        coinXs.add(Gdx.graphics.getWidth());
    }
    public void makeBomb()
    {

        float height=random.nextFloat()*Gdx.graphics.getHeight();
        coiny.add((int)height);
        coinx.add(Gdx.graphics.getWidth());
    }



	@Override
	public void render () {
        batch.begin();
        batch.draw(background,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
      if(gamestate==1)
      {
          if(bombCount<250)
          {
              bombCount++;
          }
          else
          {
              bombCount=0;
              makeBomb();
          }
          bombrectangles.clear();
          for(int i=0;i<coinx.size();i++)
          {
              batch.draw(bomb,coinx.get(i),coiny.get(i));
              coinx.set(i,coinx.get(i)-6);
              bombrectangles.add(new Rectangle(coinx.get(i),coiny.get(i),bomb.getWidth(),bomb.getHeight()));
          }
          if(coinCount<100)
          {
              coinCount++;
          }
          else
          {
              coinCount=0;
              makeCoin();
          }
          coinrectangles.clear();
          for(int i=0;i<coinXs.size();i++)
          {
              batch.draw(coin,coinXs.get(i),coinYs.get(i));
              coinXs.set(i,coinXs.get(i)-4);
              coinrectangles.add(new Rectangle(coinXs.get(i),coinYs.get(i),coin.getWidth(),coin.getHeight()));
          }
          if(Gdx.input.justTouched())
          {
              velocity=-5;
          }



          if(pause<8)
          {
              pause++ ;
          }
          else
          {
              pause=0;
              if(manstate<3)
              {
                  manstate++;
              }
              else manstate=0;
          }
          velocity+=gravity;
          manY-=velocity;
          if(manY<=0)
          {
              manY=0;
          }
      }
      else if(gamestate==0)
      {
        if(Gdx.input.justTouched())
        {
            gamestate=1;
        }
      }
      else if(gamestate==2)
      {
          if(Gdx.input.justTouched())
          {
              gamestate=1;
              manY=Gdx.graphics.getHeight()/2;
              velocity=0;
              score=0;
              coinXs.clear();
              coinYs.clear();
              coinrectangles.clear();
              coinCount=0;
              bombCount=0;
              coinx.clear();
              coiny.clear();
              bombrectangles.clear();
          }
      }
if(gamestate==2)
{
    batch.draw(dizzy,Gdx.graphics.getWidth()/2-man[manstate].getWidth()/2,manY);

}
     else  { batch.draw(man[manstate],Gdx.graphics.getWidth()/2-man[manstate].getWidth()/2,manY);
      } manRectangle=new Rectangle(Gdx.graphics.getWidth()/2-man[manstate].getWidth()/2,manY,man[manstate].getWidth(),man[manstate].getHeight());
     for (int i=0;i<coinrectangles.size();i++)
     {
         if(Intersector.overlaps(manRectangle,coinrectangles.get(i)))
         {

             score++;
             coinrectangles.remove(i);
             coinXs.remove(i);
             coinYs.remove(i);
             break;
         }

     }
        for (int i=0;i<bombrectangles.size();i++)
        {
            if(Intersector.overlaps(manRectangle,bombrectangles.get(i)))
            {
                 gamestate=2;

            }

        }
        bitmapFont.draw(batch,String.valueOf(score),100,200);
        batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();

	}
}

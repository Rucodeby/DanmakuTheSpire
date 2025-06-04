package danmaku.helpers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import danmaku.interfaces.ICollidable;


public class QuadTree
{
    //  --- Configuration
    private static final int MAX_OBJECTS_BY_NODE = 10;
    private static final int MAX_LEVEL = 6;
    //  ---

    private int level;
    private Array<ICollidable> objects;
    private Rectangle bounds;
    private QuadTree[] nodes;

    public QuadTree(int level, Rectangle bounds)
    {
        this.level = level;
        this.bounds = bounds;
        objects = new Array<ICollidable>();
        nodes = new QuadTree[4];
    }

    public void getZones(Array<Rectangle> allZones)
    {
        allZones.add(bounds);
        if (nodes[0] != null)
        {
            nodes[0].getZones(allZones);
            nodes[1].getZones(allZones);
            nodes[2].getZones(allZones);
            nodes[3].getZones(allZones);
        }
    }

    public void clear()
    {
        objects.clear();

        for (QuadTree node : nodes) {
            if (node != null) {
                node.clear();
                node = null;
            }
        }
    }

    public void insert(ICollidable rect)
    {
        if (nodes[0] != null)
        {
            int index = getIndex(rect);
            if (index != -1)
            {
                nodes[index].insert(rect);
                return;
            }
        }

        objects.add(rect);

        if (objects.size > MAX_OBJECTS_BY_NODE && level < MAX_LEVEL)
        {
            if (nodes[0] == null)
                split();

            int i = 0;
            while(i < objects.size)
            {
                int index = getIndex(objects.get(i));

                if (index != -1)
                    nodes[index].insert(objects.removeIndex(i));
                else
                    i++;
            }
        }
    }

    public Array<ICollidable> retrieve(Array<ICollidable> list, ICollidable area)
    {
        int index = getIndex(area);

        if (index != -1 & nodes[0] != null)
            nodes[index].retrieve(list, area);

        list.addAll(objects);

        return list;
    }

    private void split()
    {
        float subWidth =  (bounds.getWidth() * 0.5f);
        float subHeight = (bounds.getHeight() * 0.5f);
        float x = bounds.getX();
        float y = bounds.getY();

        nodes[0] = new QuadTree(level+1, new Rectangle(x + subWidth, y, subWidth, subHeight));
        nodes[1] = new QuadTree(level+1, new Rectangle(x, y, subWidth, subHeight));
        nodes[2] = new QuadTree(level+1, new Rectangle(x, y + subHeight, subWidth, subHeight));
        nodes[3] = new QuadTree(level+1, new Rectangle(x + subWidth, y + subHeight, subWidth, subHeight));

    }

    private int getIndex(ICollidable pRect)
    {
        int index = -1;
        float verticalMidpoint = bounds.getX() + (bounds.getWidth() * 0.5f);
        float horizontalMidpoint = bounds.getY() + (bounds.getHeight() * 0.5f);

        boolean topQuadrant = (pRect.getY() < horizontalMidpoint && pRect.getY() + pRect.getHeight() < horizontalMidpoint);
        boolean bottomQuadrant = (pRect.getY() > horizontalMidpoint);

        if (pRect.getX() < verticalMidpoint && pRect.getX() + pRect.getWidth() < verticalMidpoint)
        {
            if (topQuadrant)
                index = 1;
            else if (bottomQuadrant)
                index = 2;
        }
        else if (pRect.getX() > verticalMidpoint)
        {
            if (topQuadrant)
                index = 0;
            else if (bottomQuadrant)
                index = 3;
        }

        return index;
    }

    public void drawDebug(SpriteBatch sb) {
        for (QuadTree currNode : nodes) {
            currNode.drawDebug(sb);
        }

        switch (level) {
            case 0:
            case 1:
                sb.setColor(Color.ORANGE);
                break;

            case 2:
                sb.setColor(Color.RED);
                break;

            case 3:
                sb.setColor(Color.GREEN);
                break;

            case 4:
                sb.setColor(Color.BLUE);
                break;

            case 5:
                sb.setColor(Color.MAGENTA);
                break;
        }

        sb.draw(ImageMaster.DEBUG_HITBOX_IMG, bounds.x, bounds.y, bounds.width, bounds.height);
    }
}
package com.dozingcatsoftware.bouncy.elements;

import static com.dozingcatsoftware.bouncy.util.MathUtils.asFloat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.dozingcatsoftware.bouncy.IFieldRenderer;

/** FieldElement subclass which represents a series of wall segments. The segments are defined in the "positions"
 * parameter as a list of [x,y] values, for example:
 * {
 * 		"class": "WallPathElement",
 * 		"positions": [[5,5], [5,10], [8,10], [5, 15]]
 * }
 * 
 * @author brian
 */

public class WallPathElement extends FieldElement {
	
	List wallBodies = new ArrayList();
	float[][] lineSegments;
	
	public void finishCreate(Map params, World world) {
		List positions = (List)params.get("positions");
		// N positions product N-1 line segments
		lineSegments = new float[positions.size()-1][];
		for(int i=0; i<lineSegments.length; i++) {
			List startpos = (List)positions.get(i);
			List endpos = (List)positions.get(i+1);
			
			float[] segment = new float[] {asFloat(startpos.get(0)), asFloat(startpos.get(1)),
					asFloat(endpos.get(0)), asFloat(endpos.get(1))};
			lineSegments[i] = segment;
			
			Body wall = Box2DFactory.createThinWall(world, segment[0], segment[1], segment[2], segment[3], 0f);
			this.wallBodies.add(wall);
		}
	}

	@Override
	public List<Body> getBodies() {
		return wallBodies;
	}

	@Override
	public void draw(IFieldRenderer renderer) {
		for(float[] segment : this.lineSegments) {
			renderer.drawLine(segment[0], segment[1], segment[2], segment[3], 
					redColorComponent(DEFAULT_WALL_RED), greenColorComponent(DEFAULT_WALL_GREEN), blueColorComponent(DEFAULT_WALL_BLUE));
		}
	}

}

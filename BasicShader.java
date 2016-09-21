package com.base.engine;

public class BasicShader extends Shader{

	private static final BasicShader instance = new BasicShader();
	
	public BasicShader(){
		
		super();
		
		addVertexShaderFromFile("basicVertex.vs");
		addFragementShaderFromFile("basicFragment.fs");
		compileShader();
		
		addUniform("transform");
		addUniform("color");
	}
	
	public static BasicShader getInstance(){
		
		return instance;
	}
	
	public void updateUniforms(Matrix4f worldMatrix, Matrix4f projectedMatrix, Material material){
		
		if(material.getTexture() != null)
			material.getTexture().bind();
		else
			RenderUtil.unbindTextures();
		
		setUniform("transform",projectedMatrix);
		setUniform("color", material.getColor());
	}
}

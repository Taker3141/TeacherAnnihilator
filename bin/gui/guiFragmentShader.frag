#version 400 core

in vec2 textureCoords;

out vec4 outColor;

uniform sampler2D guiTexture;

void main(void)
{
	vec4 textureColor = texture(guiTexture,textureCoords);
	outColor = textureColor;
	if(textureColor.a < 0.5)
	{
		discard;
	}
}
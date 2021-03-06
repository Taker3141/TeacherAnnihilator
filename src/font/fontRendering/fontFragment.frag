#version 400 core

in vec2 passTextureCoord;

out vec4 outColor;

uniform vec3 color;
uniform sampler2D fontAtlas;

void main(void)
{
	outColor = vec4(color, texture(fontAtlas, passTextureCoord).a);
}
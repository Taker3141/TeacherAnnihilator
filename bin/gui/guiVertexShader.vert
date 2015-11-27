#version 400 core

in vec2 position;

out vec2 textureCoords;

uniform mat4 transformationMatrix;

void main(void)
{
	gl_Position = transformationMatrix * vec4(position, 0, 1);
	textureCoords = vec2(position.x, -position.y);
}
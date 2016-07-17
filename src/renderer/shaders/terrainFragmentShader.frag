#version 400 core

in vec2 passTextureCoord;
in vec3 surfaceNormal;
in vec3 toLightVector[4];
in vec3 toCameraVector;
in float visibility;

out vec4 outColor;

uniform sampler2D backgroundTexture;
uniform sampler2D rTexture;
uniform sampler2D gTexture;
uniform sampler2D bTexture;
uniform sampler2D blendMap;
uniform vec3 skyColor;

uniform vec3 lightColor[4];

void main(void)
{
	vec4 blendMapColor = texture(blendMap, passTextureCoord);
	
	float backTextureAmount = 1 - (blendMapColor.r + blendMapColor.g + blendMapColor.b);
	vec2 tiledCoords = passTextureCoord * 50;
	vec4 backgroundTextureColor = texture(backgroundTexture, tiledCoords) * backTextureAmount;
	vec4 rTextureColor = texture(rTexture, tiledCoords) * blendMapColor.r;
	vec4 gTextureColor = texture(gTexture, tiledCoords) * blendMapColor.g;
	vec4 bTextureColor = texture(bTexture, tiledCoords) * blendMapColor.b;
	vec4 totalColor = backgroundTextureColor + rTextureColor + gTextureColor + bTextureColor;
	
	vec3 unitNormal = normalize(surfaceNormal);
	vec3 unitCameraVector = normalize(toCameraVector);
	
	vec3 totalDiffuse = vec3(0);
	
	for(int i = 0; i < 4; i++)
	{
		vec3 unitLightVector = normalize(toLightVector[i]);
		vec3 lightDirection = -unitLightVector;
		vec3 reflectedLightDirection = reflect(lightDirection, unitNormal);
		
		float brightness = dot(unitNormal, unitLightVector);
		brightness += 0.05;
		brightness = max(brightness, 0);
		totalDiffuse = totalDiffuse + brightness * lightColor[i];
	}
	
	totalDiffuse = max(totalDiffuse, 0.2);
	
	outColor = vec4(totalDiffuse, 1.0) * totalColor;
	outColor = mix(vec4(skyColor, 1.0), outColor, visibility);
}
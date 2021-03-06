#version 400 core

in vec2 passTextureCoord;
in vec3 surfaceNormal;
in vec3 toLightVector[4];
in vec3 toCameraVector;
in float visibility;

out vec4 outColor;

uniform sampler2D textureSampler;
uniform vec3 lightColor[4];
uniform float shineDamper;
uniform float reflectivity;
uniform vec3 skyColor;

void main(void)
{
	vec3 unitNormal = normalize(surfaceNormal);
	vec3 unitCameraVector = normalize(toCameraVector);
	
	vec3 totalDiffuse = vec3(0);
	vec3 totalSpecular = vec3(0);
	
	for(int i = 0; i < 4; i++)
	{
		vec3 unitLightVector = normalize(toLightVector[i]);
		vec3 lightDirection = -unitLightVector;
		vec3 reflectedLightDirection = reflect(lightDirection, unitNormal);
		
		float brightness = dot(unitNormal, unitLightVector);
		brightness = max(brightness, 0.0);
		
		float specularFactor = dot(reflectedLightDirection, unitCameraVector);
		specularFactor = max(specularFactor, 0.0);
		float dampedFactor = pow(specularFactor, shineDamper);;
		totalDiffuse = totalDiffuse + brightness * lightColor[i];
		totalSpecular = totalSpecular + dampedFactor * lightColor[i] * reflectivity;
	}
	
	totalDiffuse = max(totalDiffuse, 0.2);
	
	vec4 textureColor = texture(textureSampler, passTextureCoord);
	if(textureColor.a < 0.5)
	{
		discard;
	}
	
	outColor = vec4(totalDiffuse, 1.0) * textureColor + vec4(totalSpecular, 1.0);
	outColor = mix(vec4(skyColor, 1.0), outColor, visibility);
}
//common

#version 150 core

uniform float blurRadius;
uniform vec2 pixelSize;
uniform sampler2D prevPass;

in vec2 texcoord;

layout(location = 0) out vec4 nextPass;

float square(float f) {
	return f * f;
}

vec3 square(vec3 color) {
	return color * color;
}

//horizontal pass

void main() {
	int ceilRadius = int(ceil(blurRadius));
	float offsetMultiplier = 2.0 / blurRadius;
	vec4 sum = vec4(square(texture(prevPass, texcoord).rgb), 1.0);
	for (int offset = 1; offset <= ceilRadius; offset++) {
		float weight = exp(-square(float(offset) * offsetMultiplier));
		vec2 coord1 = vec2(texcoord.x - float(offset) * pixelSize.x, texcoord.y);
		vec2 coord2 = vec2(texcoord.x + float(offset) * pixelSize.x, texcoord.y);
		sum += vec4(square(texture(prevPass, coord1).rgb) * weight, weight);
		sum += vec4(square(texture(prevPass, coord2).rgb) * weight, weight);
	}
	nextPass = vec4(sqrt(sum.rgb / sum.a), 1.0);
}

//vertical pass

void main() {
	int ceilRadius = int(ceil(blurRadius));
	float offsetMultiplier = 2.0 / blurRadius;
	vec4 sum = vec4(square(texture(prevPass, texcoord).rgb), 1.0);
	for (int offset = 1; offset <= ceilRadius; offset++) {
		float weight = exp(-square(float(offset) * offsetMultiplier));
		vec2 coord1 = vec2(texcoord.x, texcoord.y - float(offset) * pixelSize.y);
		vec2 coord2 = vec2(texcoord.x, texcoord.y + float(offset) * pixelSize.y);
		sum += vec4(square(texture(prevPass, coord1).rgb) * weight, weight);
		sum += vec4(square(texture(prevPass, coord2).rgb) * weight, weight);
	}
	nextPass = vec4(sqrt(sum.rgb / sum.a), 1.0);
}

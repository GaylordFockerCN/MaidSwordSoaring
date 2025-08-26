#version 150

#moj_import <fog.glsl>
#define PI 3.14159265

uniform sampler2D Texture;
uniform sampler2D Noise2;

uniform vec4 ColorModulator;
uniform float FogStart;
uniform float FogEnd;
uniform vec4 FogColor;
uniform float GameTime;

uniform vec4 HDR_Color1;
uniform vec4 HDR_Color2;
uniform float NoiseFlitter;
uniform float TimeFactor;

//in float vertexDistance;
in vec2 texCoord0;
in vec4 vertexColor;
//in vec3 vertexNormal;

out vec4 fragColor;

float SamplerNoise2(float fliter){
    vec2 nuv = vec2(texCoord0);
    nuv.x = nuv.x * 4;
    nuv.x = nuv.x - floor(nuv.x);
    nuv.y = nuv.y / 2 - GameTime * 1200 * TimeFactor;
    nuv.y = nuv.y - floor(nuv.y);
    float n = texture(Noise2, nuv).r;
    if(n > fliter) return 1;
    else return 0;
}

void main() {
    vec4 color = texture(Texture, vec2(texCoord0.x - GameTime * 500, texCoord0.y)) * vertexColor * ColorModulator;

    float wave_factor = 0.08 * sin(GameTime * 12000 * TimeFactor);

    float noise2 = SamplerNoise2(NoiseFlitter - wave_factor);


    vec4 HDRCOL = (HDR_Color1 + HDR_Color2 * noise2) / (1+noise2);

    fragColor = color * HDRCOL;
}
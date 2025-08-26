// have to use 330+
#version 330 core

#moj_import <fog.glsl>
// Photon2 vertex helper
#moj_import <photon:particle.glsl>

uniform sampler2D Sampler2;

uniform mat4 ModelViewMat;
uniform mat4 ProjMat;
uniform int FogShape;

uniform float GameTime;

//out float vertexDistance;
out vec2 texCoord0;
out vec4 vertexColor;
//out vec3 vertexNormal;

void main() {
    ParticleData data = getParticleData();
    gl_Position = ProjMat * ModelViewMat * vec4(data.Position, 1.0);
    //vertexDistance = fog_distance(data.Position, FogShape);
    texCoord0 = data.UV;
    vertexColor = data.Color * texelFetch(Sampler2, data.LightUV / 16, 0);
    //vertexNormal = normalize(mat3(ModelViewMat) * data.Normal);

}
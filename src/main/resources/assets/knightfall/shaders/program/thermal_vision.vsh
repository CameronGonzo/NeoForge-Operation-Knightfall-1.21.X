#version 150

in vec4 Position;
out vec2 texCoord;

uniform mat4 ProjMat;

void main() {
    gl_Position = ProjMat * vec4(Position.xy, 0.0, 1.0);
    texCoord = Position.zw;
}

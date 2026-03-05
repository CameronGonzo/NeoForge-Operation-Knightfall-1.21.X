#version 150

uniform sampler2D DiffuseSampler;
uniform float Opacity;

in vec2 texCoord;
out vec4 fragColor;

void main() {
    vec4 original = texture(DiffuseSampler, texCoord);
    vec4 blueOverlay = vec4(0.0, 0.0, 1.0, Opacity);
    fragColor = mix(original, blueOverlay, blueOverlay.a);
}

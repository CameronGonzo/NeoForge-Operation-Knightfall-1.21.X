#version 150

uniform sampler2D DiffuseSampler;  // The game screen texture
uniform float Time;                // Animation over time
uniform vec2 ScreenSize;           // Screen resolution

in vec2 texCoord;  // The texture coordinate input
out vec4 fragColor;  // The final color output

void main() {
    vec2 uv = texCoord;
    vec2 center = vec2(0.5, 0.5);  // Screen center
    vec2 delta = uv - center;
    float dist = length(delta);

    // Ripple effect using sine wave
    float ripple = sin(dist * 20.0 - Time * 4.0) * 0.01;
    uv += normalize(delta) * ripple;

    // Sample the distorted screen texture
    fragColor = texture(DiffuseSampler, uv);
}

#version 150

uniform sampler2D DiffuseSampler;
in vec2 texCoord;
out vec4 fragColor;

void main() {
    vec4 color = texture(DiffuseSampler, texCoord);

    float intensity = (color.r * 0.3 + color.g * 0.59 + color.b * 0.11);

    vec3 thermalColor = vec3(0.0);
    thermalColor.r = smoothstep(0.4, 1.0, intensity);
    thermalColor.g = smoothstep(0.2, 0.8, intensity) * 0.6;
    thermalColor.b = smoothstep(0.0, 0.5, intensity) * 0.3;

    fragColor = vec4(thermalColor, 1.0);
}

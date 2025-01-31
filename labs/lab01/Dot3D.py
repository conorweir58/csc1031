#!/usr/bin/env python3

class Dot3D:

    def __init__(self, x, y, z, label=None):
        self.x = x
        self.y = y
        self.z = z
        self.label = label

    def distance_to(self, p2):
        return ( ((p2.x - self.x) * (p2.x - self.x)) + ((p2.y - self.y) * (p2.y - self.y)) + ((p2.z - self.z) * (p2.z - self.z))) ** 0.5
    
    def add_vector(self, p2):
        return Dot3D((self.x + p2.x), (self.y + p2.y), (self.z + p2.z), f"{self.label}+{p2.label}")
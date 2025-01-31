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

class Triangle3D:
    def __init__(self, dot1, dot2, dot3):
        self.dot1 = dot1
        self.dot2 = dot2
        self.dot3 = dot3
        
        self.edge1 = dot1.distance_to(dot2)
        self.edge2 = dot2.distance_to(dot3)
        self.edge3 = dot3.distance_to(dot1)

    def calculate_perimeter(self):
        return self.edge1 + self.edge2 + self.edge3
        

    def calculate_area(self):
        s = (self.edge1 + self.edge2 + self.edge3) / 2
        return ( (s * (s - self.edge1) * (s - self.edge2) * (s - self.edge3) ) ** 0.5)
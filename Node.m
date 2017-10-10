classdef Node < matlab.mixin.SetGet
    %NODE Summary of this class goes here
    %   Detailed explanation goes here
    
    properties
        connected
        distance
        angle
        xcoord
        ycoord
        basevector
        basemag
    end
    
    methods
        function nodeObj = Node(xcoord, ycoord)
            nodeObj.xcoord = xcoord;
            nodeObj.ycoord = ycoord;
        end
        function insertNode(nodeObj, node)
            if(nodeObj.connected ~= null)
                node.setConnected(nodeObj.connected);
                nodeObj.setConnected(node);
            end
        end
        function set.connected(nodeObj, node)
            nodeObj.distance = ((nodeObj.xcoord-node.xcoord)^2+ (nodeObj.ycoord-node.ycoord)^2)^(1/2);
            
            dot_product = ((nodeObj.xcoord-node.xcoord)*nodeObj.basevector(1) + (nodeObj.ycoord-node.ycoord)*nodeObj.basevector(2));
            magnitude_basevector = (nodeObj.basevector(1)^2 + nodeObj.basevector(2)^2)^(1/2);
            magnitude_vector = ((nodeObj.xcoord-node.xcoord)^2 + (nodeObj.ycoord-node.ycoord)^2)^(1/2);
            nodeObj.angle = (dot_product/(magnitude_basevector*magnitude_vector));
            nodeObj.connected = node;
        end
        function angle = angleToVector(nodeObj, node)
            dot_product = abs(dot(nodeObj.basevector, node.basevector));
            angle = acos(dot_product/(nodeObj.basemag*node.basemag))*180/pi;
        end
        function flag = equals(nodeObj, node)
            if(nodeObj.xcoord == node.xcoord && nodeObj.ycoord == node.ycoord)
                flag = true;
            else
                flag = false;
            end
        end
        function [r] = path(nodeObj, end)
            
            if(isempty(nodeObj.connected)|| nodeObj.equals(end))
                r = nodeObj;
            else
                r = [nodeObj, nodeObj.connected.path(end)];
            end
        end
    end
    
end


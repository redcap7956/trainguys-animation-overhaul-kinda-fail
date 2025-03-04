package gg.moonflower.animationoverhaul.util.data;

import com.google.common.collect.Maps;
import gg.moonflower.animationoverhaul.AnimationOverhaulMain;
import gg.moonflower.animationoverhaul.util.time.ChannelTimeline;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;

import java.util.Map;

public class TimelineGroupData {

    public static TimelineGroupData INSTANCE = new TimelineGroupData();

    //TODO: Set this as a class,

    // Entity type entries -> Specific animation entries -> Part entries -> Timeline
    private final Map<ResourceLocation, TimelineGroup> animationEntries = Maps.newHashMap();

    public TimelineGroupData(){
    }

    public void put(ResourceLocation resourceLocation, TimelineGroup timelineGroup){
        animationEntries.put(resourceLocation, timelineGroup);
    }

    public TimelineGroup get(ResourceLocation resourceLocation){
        if(animationEntries.containsKey(resourceLocation)){
            return animationEntries.get(resourceLocation);
        } else {
            //AnimationOverhaulMain.LOGGER.error("Resource location {} not found in loaded data", resourceLocation);
            return TimelineGroup.blank();
        }
    }

    public TimelineGroup get(String namespace, String path){
        return get(new ResourceLocation(namespace, path));
    }

    public TimelineGroup get(String namespace, EntityType<?> entityType, String animationKey){
        return get(namespace, entityType.toShortString() + "/" + animationKey);
    }

    public void clearAndReplace(TimelineGroupData newAnimationData){
        this.animationEntries.clear();
        for(ResourceLocation resourceLocation : newAnimationData.getHashMap().keySet()){
            this.put(resourceLocation, newAnimationData.get(resourceLocation));
        }
    }

    public Map<ResourceLocation, TimelineGroup> getHashMap(){
        return animationEntries;
    }

    public static class TimelineGroup {

        private Map<String, ChannelTimeline> partTimelines = Maps.newHashMap();
        private final float frameLength;

        public TimelineGroup(float timelineFrameLength){
            frameLength = timelineFrameLength;
        }

        public ChannelTimeline getPartTimeline(String partName){
            return partTimelines.get(partName);
        }

        public float getFrameLength(){
            return frameLength;
        }

        public boolean containsPart(String partName){
            return partTimelines.containsKey(partName);
        }

        public void addPartTimeline(String partName, ChannelTimeline partTimeline){
            partTimelines.put(partName, partTimeline);
        }

        public static TimelineGroup blank(){
            return new TimelineGroup(10);
        }
    }
}

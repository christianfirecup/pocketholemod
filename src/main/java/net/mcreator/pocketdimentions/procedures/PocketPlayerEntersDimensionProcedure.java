package net.mcreator.pocketdimentions.procedures;

import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.World;
import net.minecraft.world.IWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.Rotation;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Mirror;
import net.minecraft.entity.Entity;

import net.mcreator.pocketdimentions.PocketDimentionsModVariables;
import net.mcreator.pocketdimentions.PocketDimentionsMod;

import java.util.Map;

public class PocketPlayerEntersDimensionProcedure {

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				PocketDimentionsMod.LOGGER.warn("Failed to load dependency world for procedure PocketPlayerEntersDimension!");
			return;
		}
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				PocketDimentionsMod.LOGGER.warn("Failed to load dependency entity for procedure PocketPlayerEntersDimension!");
			return;
		}
		IWorld world = (IWorld) dependencies.get("world");
		Entity entity = (Entity) dependencies.get("entity");
		if (PocketDimentionsModVariables.WorldVariables.get(world).firstenter == false) {
			PocketDimentionsModVariables.WorldVariables.get(world).firstenter = (boolean) (true);
			PocketDimentionsModVariables.WorldVariables.get(world).syncData(world);
			if (world instanceof ServerWorld) {
				Template template = ((ServerWorld) world).getStructureTemplateManager()
						.getTemplateDefaulted(new ResourceLocation("pocket_dimentions", "testbox"));
				if (template != null) {
					template.func_237144_a_((ServerWorld) world,
							new BlockPos((int) (entity.getPosX() - 18), (int) (entity.getPosY() - 3), (int) (entity.getPosZ() - 18)),
							new PlacementSettings().setRotation(Rotation.COUNTERCLOCKWISE_90).setMirror(Mirror.FRONT_BACK).setChunk(null)
									.setIgnoreEntities(false),
							((World) world).rand);
				}
			}
		}
	}
}

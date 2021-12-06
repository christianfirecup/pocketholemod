package net.mcreator.pocketdimentions.procedures;

import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.World;
import net.minecraft.world.IWorld;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.Rotation;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.Mirror;
import net.minecraft.potion.EffectInstance;
import net.minecraft.network.play.server.SPlayerAbilitiesPacket;
import net.minecraft.network.play.server.SPlaySoundEventPacket;
import net.minecraft.network.play.server.SPlayEntityEffectPacket;
import net.minecraft.network.play.server.SChangeGameStatePacket;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.Entity;

import net.mcreator.pocketdimentions.PocketDimentionsMod;

import java.util.Map;

public class DimentionRightClickedInAirProcedure {
	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				PocketDimentionsMod.LOGGER.warn("Failed to load dependency entity for procedure DimentionRightClickedInAir!");
			return;
		}
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				PocketDimentionsMod.LOGGER.warn("Failed to load dependency world for procedure DimentionRightClickedInAir!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		IWorld world = (IWorld) dependencies.get("world");
		{
			Entity _ent = entity;
			if (!_ent.world.isRemote && _ent instanceof ServerPlayerEntity) {
				RegistryKey<World> destinationType = RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("pocket_dimentions:pocket"));
				ServerWorld nextWorld = _ent.getServer().getWorld(destinationType);
				if (nextWorld != null) {
					((ServerPlayerEntity) _ent).connection.sendPacket(new SChangeGameStatePacket(SChangeGameStatePacket.field_241768_e_, 0));
					((ServerPlayerEntity) _ent).teleport(nextWorld, nextWorld.getSpawnPoint().getX(), nextWorld.getSpawnPoint().getY() + 1,
							nextWorld.getSpawnPoint().getZ(), _ent.rotationYaw, _ent.rotationPitch);
					((ServerPlayerEntity) _ent).connection.sendPacket(new SPlayerAbilitiesPacket(((ServerPlayerEntity) _ent).abilities));
					for (EffectInstance effectinstance : ((ServerPlayerEntity) _ent).getActivePotionEffects()) {
						((ServerPlayerEntity) _ent).connection.sendPacket(new SPlayEntityEffectPacket(_ent.getEntityId(), effectinstance));
					}
					((ServerPlayerEntity) _ent).connection.sendPacket(new SPlaySoundEventPacket(1032, BlockPos.ZERO, 0, false));
				}
			}
		}
		if (world instanceof ServerWorld) {
			Template template = ((ServerWorld) world).getStructureTemplateManager()
					.getTemplateDefaulted(new ResourceLocation("pocket_dimentions", "humblecottage"));
			if (template != null) {
				template.func_237144_a_((ServerWorld) world,
						new BlockPos((int) (entity.getPosX()), (int) (entity.getPosY()), (int) (entity.getPosZ())),
						new PlacementSettings().setRotation(Rotation.CLOCKWISE_90).setMirror(Mirror.NONE).setChunk(null).setIgnoreEntities(false),
						((World) world).rand);
			}
		}
	}
}

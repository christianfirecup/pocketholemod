package net.mcreator.pocketdimentions.procedures;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.common.MinecraftForge;

import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.IWorld;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.RegistryKey;
import net.minecraft.potion.EffectInstance;
import net.minecraft.network.play.server.SPlayerAbilitiesPacket;
import net.minecraft.network.play.server.SPlaySoundEventPacket;
import net.minecraft.network.play.server.SPlayEntityEffectPacket;
import net.minecraft.network.play.server.SChangeGameStatePacket;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.Entity;

import net.mcreator.pocketdimentions.PocketDimentionsModVariables;
import net.mcreator.pocketdimentions.PocketDimentionsMod;

import java.util.Map;
import java.util.Collections;

public class DimentionRightClickedInAirProcedure {

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				PocketDimentionsMod.LOGGER.warn("Failed to load dependency world for procedure DimentionRightClickedInAir!");
			return;
		}
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				PocketDimentionsMod.LOGGER.warn("Failed to load dependency entity for procedure DimentionRightClickedInAir!");
			return;
		}
		IWorld world = (IWorld) dependencies.get("world");
		Entity entity = (Entity) dependencies.get("entity");
		if ((entity.getCapability(PocketDimentionsModVariables.PLAYER_VARIABLES_CAPABILITY, null)
				.orElse(new PocketDimentionsModVariables.PlayerVariables())).timer == true) {
			if ((entity.world.getDimensionKey()) == (World.OVERWORLD)) {
				{
					double _setval = (double) (entity.getPosX());
					entity.getCapability(PocketDimentionsModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
						capability.playerx = _setval;
						capability.syncPlayerVariables(entity);
					});
				}
				{
					double _setval = (double) (entity.getPosY());
					entity.getCapability(PocketDimentionsModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
						capability.playery = _setval;
						capability.syncPlayerVariables(entity);
					});
				}
				{
					double _setval = (double) (entity.getPosZ());
					entity.getCapability(PocketDimentionsModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
						capability.playerz = _setval;
						capability.syncPlayerVariables(entity);
					});
				}
				{
					Entity _ent = entity;
					if (!_ent.world.isRemote && _ent instanceof ServerPlayerEntity) {
						RegistryKey<World> destinationType = RegistryKey.getOrCreateKey(Registry.WORLD_KEY,
								new ResourceLocation("pocket_dimentions:pocket"));
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
				if ((entity.world.getDimensionKey()) == (RegistryKey.getOrCreateKey(Registry.WORLD_KEY,
						new ResourceLocation("pocket_dimentions:pocket")))) {
					{
						Entity _ent = entity;
						if (!_ent.world.isRemote && _ent instanceof ServerPlayerEntity) {
							RegistryKey<World> destinationType = World.OVERWORLD;
							ServerWorld nextWorld = _ent.getServer().getWorld(destinationType);
							if (nextWorld != null) {
								((ServerPlayerEntity) _ent).connection
										.sendPacket(new SChangeGameStatePacket(SChangeGameStatePacket.field_241768_e_, 0));
								((ServerPlayerEntity) _ent).teleport(nextWorld, nextWorld.getSpawnPoint().getX(),
										nextWorld.getSpawnPoint().getY() + 1, nextWorld.getSpawnPoint().getZ(), _ent.rotationYaw, _ent.rotationPitch);
								((ServerPlayerEntity) _ent).connection.sendPacket(new SPlayerAbilitiesPacket(((ServerPlayerEntity) _ent).abilities));
								for (EffectInstance effectinstance : ((ServerPlayerEntity) _ent).getActivePotionEffects()) {
									((ServerPlayerEntity) _ent).connection
											.sendPacket(new SPlayEntityEffectPacket(_ent.getEntityId(), effectinstance));
								}
								((ServerPlayerEntity) _ent).connection.sendPacket(new SPlaySoundEventPacket(1032, BlockPos.ZERO, 0, false));
							}
						}
					}
					{
						Entity _ent = entity;
						_ent.setPositionAndUpdate(
								((entity.getCapability(PocketDimentionsModVariables.PLAYER_VARIABLES_CAPABILITY, null)
										.orElse(new PocketDimentionsModVariables.PlayerVariables())).playerx),
								((entity.getCapability(PocketDimentionsModVariables.PLAYER_VARIABLES_CAPABILITY, null)
										.orElse(new PocketDimentionsModVariables.PlayerVariables())).playery),
								((entity.getCapability(PocketDimentionsModVariables.PLAYER_VARIABLES_CAPABILITY, null)
										.orElse(new PocketDimentionsModVariables.PlayerVariables())).playerz));
						if (_ent instanceof ServerPlayerEntity) {
							((ServerPlayerEntity) _ent).connection.setPlayerLocation(
									((entity.getCapability(PocketDimentionsModVariables.PLAYER_VARIABLES_CAPABILITY, null)
											.orElse(new PocketDimentionsModVariables.PlayerVariables())).playerx),
									((entity.getCapability(PocketDimentionsModVariables.PLAYER_VARIABLES_CAPABILITY, null)
											.orElse(new PocketDimentionsModVariables.PlayerVariables())).playery),
									((entity.getCapability(PocketDimentionsModVariables.PLAYER_VARIABLES_CAPABILITY, null)
											.orElse(new PocketDimentionsModVariables.PlayerVariables())).playerz),
									_ent.rotationYaw, _ent.rotationPitch, Collections.emptySet());
						}
					}
					{
						boolean _setval = (boolean) (false);
						entity.getCapability(PocketDimentionsModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
							capability.timer = _setval;
							capability.syncPlayerVariables(entity);
						});
					}
				}
			}
		} else if ((entity.getCapability(PocketDimentionsModVariables.PLAYER_VARIABLES_CAPABILITY, null)
				.orElse(new PocketDimentionsModVariables.PlayerVariables())).timer == false) {
			if (entity instanceof PlayerEntity && !entity.world.isRemote()) {
				((PlayerEntity) entity).sendStatusMessage(new StringTextComponent("Please wait 20 seconds"), (true));
			}
			while ((entity.getCapability(PocketDimentionsModVariables.PLAYER_VARIABLES_CAPABILITY, null)
					.orElse(new PocketDimentionsModVariables.PlayerVariables())).number > 0) {
				new Object() {
					private int ticks = 0;
					private float waitTicks;
					private IWorld world;

					public void start(IWorld world, int waitTicks) {
						this.waitTicks = waitTicks;
						MinecraftForge.EVENT_BUS.register(this);
						this.world = world;
					}

					@SubscribeEvent
					public void tick(TickEvent.ServerTickEvent event) {
						if (event.phase == TickEvent.Phase.END) {
							this.ticks += 1;
							if (this.ticks >= this.waitTicks)
								run();
						}
					}

					private void run() {
						{
							double _setval = (double) ((entity.getCapability(PocketDimentionsModVariables.PLAYER_VARIABLES_CAPABILITY, null)
									.orElse(new PocketDimentionsModVariables.PlayerVariables())).number - 1);
							entity.getCapability(PocketDimentionsModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
								capability.number = _setval;
								capability.syncPlayerVariables(entity);
							});
						}
						MinecraftForge.EVENT_BUS.unregister(this);
					}
				}.start(world, (int) 20);
			}
			if ((entity.getCapability(PocketDimentionsModVariables.PLAYER_VARIABLES_CAPABILITY, null)
					.orElse(new PocketDimentionsModVariables.PlayerVariables())).number == 0) {
				{
					double _setval = (double) 20;
					entity.getCapability(PocketDimentionsModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
						capability.number = _setval;
						capability.syncPlayerVariables(entity);
					});
				}
				{
					boolean _setval = (boolean) (true);
					entity.getCapability(PocketDimentionsModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
						capability.timer = _setval;
						capability.syncPlayerVariables(entity);
					});
				}
			}
		}
	}
}
